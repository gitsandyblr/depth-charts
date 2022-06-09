package org.fanduel.depthcharts.repository.sport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fanduel.depthcharts.repository.team.Team;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "sports")
public class Sport {

  @Id
  @Column(name = "sports_id", unique = true, nullable = false)
  private int sportId;

  @Column(name = "sports_name", unique = true, nullable = false)
  private String sportName;

//  @OneToMany(
//    cascade = CascadeType.ALL,
//    orphanRemoval = true
//  )
//  private List<Team> comments = new ArrayList<>();

//  @OneToMany(mappedBy="sports")
//  private Set<Team> teams;
}
