package org.fanduel.depthcharts.repository.team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TeamRepositoryWrapper {

  @Autowired
  TeamRepository teamRepository;

  public Team addTeam(Team team){
    return teamRepository.save(team);
  }
}
