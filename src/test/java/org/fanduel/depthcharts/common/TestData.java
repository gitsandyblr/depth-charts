package org.fanduel.depthcharts.common;

import org.fanduel.depthcharts.repository.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TestData {

  public static Optional<List<Player>> getFullDepthChartData() {
    List<Player> playerList = new ArrayList<>();
    playerList.add(Player.builder().playerId(12).playerName("Tom Brady").playerPosition("QB").build());
    playerList.add(Player.builder().playerId(11).playerName("Blaine Gabbert").playerPosition("QB").build());
    playerList.add(Player.builder().playerId(2).playerName("Kyle Trask").playerPosition("QB").build());
    playerList.add(Player.builder().playerId(13).playerName("Mike Evans").playerPosition("LWR").build());
    playerList.add(Player.builder().playerId(1).playerName("Jaelon Darden").playerPosition("LWR").build());
    playerList.add(Player.builder().playerId(10).playerName("Scott Miller").playerPosition("LWR").build());
    return Optional.ofNullable(playerList);
  }
}
