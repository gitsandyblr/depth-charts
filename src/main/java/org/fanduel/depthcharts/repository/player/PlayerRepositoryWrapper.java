package org.fanduel.depthcharts.repository.player;

import org.fanduel.depthcharts.api.PlayerDto;
import org.fanduel.depthcharts.exception.DepthChartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
public class PlayerRepositoryWrapper {

  @Autowired
  PlayerRepository repository;

  @Transactional
  public Player addPlayerToDepthChart(PlayerDto playerDto) {

    Optional<Player> playerOptional = Optional.ofNullable(repository.findPlayersByName(playerDto.getPlayerName()));
    Player player;

    if (playerOptional.isEmpty()) {
      player = Player.builder().playerName(playerDto.getPlayerName()).playerPosition(playerDto.getPlayerPosition())
        .playerPositionDepth(playerDto.getPlayerPositionDepth()).playerSportsId(1).playerTeamId(768).build();
    } else {
      player = playerOptional.get();
    }

    List<Player> backUpPlayers = repository.getExistingDepthPosition(
      player.getPlayerPosition(), player.getPlayerPositionDepth());

    //check if depth position already exists
    boolean depthPositionFlag = backUpPlayers.stream().anyMatch(entry ->
      entry.getPlayerPositionDepth() == player.getPlayerPositionDepth());

    //save the player to the chart
    repository.save(player);

    //update the depth of the player affected by new addition
    if(depthPositionFlag) {
      backUpPlayers.stream()
        .forEach(player2 -> repository.updateDepthPosition(player2.getPlayerPositionDepth() +1, player2.getPlayerId()));
    }

    return player;

  }

  @Transactional
  public Player removePlayerFromDepthChart(PlayerDto playerDto) throws DepthChartException {

    Optional<Player> playerOptional = Optional.ofNullable(repository.findPlayersByName(playerDto.getPlayerName()));

    if (playerOptional.isEmpty()) {
      throw new DepthChartException("Player does exists to be added in depth chart");
    }

    Player dbRecord = playerOptional.get();
    List<Player> backUpPlayers = repository.getBackupsByPositionAndDepth(
      dbRecord.getPlayerPosition(), dbRecord.getPlayerPositionDepth());

    //delete the player from table
    repository.delete(dbRecord);

    //update the depth of the player affected by new addition
    backUpPlayers.stream()
      .forEach(player2 -> repository.updateDepthPosition(player2.getPlayerPositionDepth() -1, player2.getPlayerId()));
    return dbRecord;

  }

  public List<Player> getBackups (String position, String player) {
    Optional<Player> existingPlayer = Optional.ofNullable(repository.getBackups(position, player));
    return existingPlayer.isPresent() ?
      repository.getBackupsByPositionAndDepth(existingPlayer.get().getPlayerPosition(), existingPlayer.get().getPlayerPositionDepth())
      : null;
  }

  public Optional<List<Player>> getFullDepthChart() {
    return Optional.ofNullable(repository.findAll());
  }
}
