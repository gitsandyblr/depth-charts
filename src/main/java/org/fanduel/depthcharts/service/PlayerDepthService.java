package org.fanduel.depthcharts.service;

import lombok.extern.slf4j.Slf4j;
import org.fanduel.depthcharts.api.PlayerDto;
import org.fanduel.depthcharts.exception.DepthChartException;
import org.fanduel.depthcharts.repository.Position.PlayerPosition;
import org.fanduel.depthcharts.repository.Position.PlayerPositionRepositoryWrapper;
import org.fanduel.depthcharts.repository.player.Player;
import org.fanduel.depthcharts.repository.player.PlayerRepositoryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PlayerDepthService {

  @Autowired
  PlayerRepositoryWrapper wrapper;

  @Autowired
  PlayerPositionRepositoryWrapper positionRepositoryWrapper;

  public Player addPlayerToDepthChart(PlayerDto playerDto) throws DepthChartException {

    return wrapper.addPlayerToDepthChart(playerDto);
  }

  public Player removePlayerToDepthChart(PlayerDto playerDto) throws DepthChartException {
    return wrapper.removePlayerFromDepthChart(playerDto);
  }

  public List<PlayerDto> getBackups (String position, String player) throws DepthChartException {
    doesPositionExists(position);
    List<Player> players = wrapper.getBackups(position, player);
    return players == null ? null : players.stream().map(player1 -> mapToPlayerEntity(player1)).collect(Collectors.toList());
  }

  public List<PlayerDto> getFullDepthChart() {
    Optional<List<Player>> players = wrapper.getFullDepthChart();

    if (players.isEmpty()) {
      return null;
    }
    List<Player> sortedPlayers = players.get().stream()
      .sorted(Comparator.comparing(Player::getPlayerPosition)
        .thenComparing(Player::getPlayerPositionDepth))
      .collect(Collectors.toList());
    return sortedPlayers.stream().map(player -> mapToPlayerEntity(player)).collect(Collectors.toList());
  }

  private PlayerDto mapToPlayerEntity(Player player) {
    return PlayerDto.builder().playerNumber(player.getPlayerId())
      .playerName(player.getPlayerName()).playerPosition(player.getPlayerPosition()).build();
  }

  private void doesPositionExists(String position) throws DepthChartException {
    try{
      Optional<PlayerPosition> playerPosition = positionRepositoryWrapper.getPlayerPositionByName(position);

      if (playerPosition.isEmpty()) {
        throw new DepthChartException("The player position does not exists");
      }
    }catch(Exception e) {
      throw new DepthChartException("Error while fetching the player position");
    }
  }
}
