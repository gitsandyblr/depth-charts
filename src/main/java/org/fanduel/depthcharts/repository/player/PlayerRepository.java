package org.fanduel.depthcharts.repository.player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {


  @Query("select player from Player player where player.playerTeamId = :playerTeamId")
  List<Player> findPlayersBySports(@Param("playerTeamId") int playerSportsId);

  @Query("select player from Player player where player.playerName = :playerName")
  Player findPlayersByName(@Param("playerName") String playerName);

  @Query("select player from Player player where player.playerPosition = :position and player.playerPositionDepth > :positionDepth")
  List<Player> getBackupsByPositionAndDepth (@Param("position") String position, @Param("positionDepth") int positionDepth);

  @Query("select player from Player player where player.playerPosition = :position and player.playerPositionDepth >= :positionDepth")
  List<Player> getExistingDepthPosition (@Param("position") String position, @Param("positionDepth") int positionDepth);

  @Query("select player from Player player where player.playerName = :playerName and player.playerPosition = :playerPosition")
  Player getBackups (@Param("playerPosition") String position, @Param("playerName") String player);

  @Modifying
  @Query("update Player player set player.playerPositionDepth = :playerPositionDepth where player.playerId = :playerId")
  void updateDepthPosition(int playerPositionDepth, int playerId);

}
