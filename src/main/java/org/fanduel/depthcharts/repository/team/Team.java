package org.fanduel.depthcharts.repository.team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fanduel.depthcharts.repository.player.Player;
import org.fanduel.depthcharts.repository.sport.Sport;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "team")
public class Team {

  @Id
  @Column(name = "team_id", unique = true, nullable = false)
  private int teamId;

  @Column(name = "team_name", unique = true, nullable = false)
  private String teamName;

//  @OneToMany(
//    cascade = CascadeType.ALL,
//    orphanRemoval = true
//  )
//  private List<Player> players = new ArrayList<>();

  @Column(name = "team_sports_id", nullable = false)
  private int teamSportsId;

//  @ManyToOne
//  @JoinColumn(name="sports_id", nullable=false, foreignKey = @ForeignKey(name = "team_sports_id"))
//  private Sport sport;
//
//
//  @OneToMany(mappedBy="team")
//  private Set<Player> players;

//  @OneToMany(cascade=CascadeType.ALL)
//  @JoinColumn(name="player_team_id")
//  private Set<Player> players;
}
