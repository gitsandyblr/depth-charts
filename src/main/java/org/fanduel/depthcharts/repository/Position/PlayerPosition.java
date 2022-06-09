package org.fanduel.depthcharts.repository.Position;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "player_position")
public class PlayerPosition {

  @Id
  @Column(name = "player_position_id", nullable = false)
  private int playerPositionId;

  @Column(name = "player_position_name", nullable = false)
  private String playerPositionName;

  @Column(name = "player_sports_id", nullable = false)
  private int playerTeamSportsId;

}
