package org.fanduel.depthcharts.repository.player;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "player")
public class Player {

  @Id
  @Column(name = "player_id", unique = true, nullable = false)
  private int playerId;

  @Column(name = "player_name", nullable = false, unique = true)
  private String playerName;

  @Column(name = "player_position")
  private String playerPosition;

  @Column(name = "player_position_depth")
  private int playerPositionDepth;

  @Column(name = "player_sports_id", nullable = false)
  private int playerSportsId;

  @Column(name = "player_team_id", nullable = false)
  private int playerTeamId;

}
