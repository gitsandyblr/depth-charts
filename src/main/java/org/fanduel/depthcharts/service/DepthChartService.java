package org.fanduel.depthcharts.service;

import org.fanduel.depthcharts.api.SportDto;
import org.fanduel.depthcharts.api.TeamDto;
import org.fanduel.depthcharts.repository.sport.Sport;
import org.fanduel.depthcharts.repository.sport.SportRepositoryWrapper;
import org.fanduel.depthcharts.repository.team.Team;
import org.fanduel.depthcharts.repository.team.TeamRepository;
import org.fanduel.depthcharts.repository.team.TeamRepositoryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepthChartService {

  @Autowired
  SportRepositoryWrapper sportRepositoryWrapper;

  @Autowired
  TeamRepositoryWrapper teamRepositoryWrapper;

  public Sport addSport(SportDto sportDto) {
    Sport sport = Sport.builder().sportId(sportDto.getSportId()).sportName(sportDto.getSportName()).build();
    return sportRepositoryWrapper.addSport(sport);
  }

  public Team addTeam(TeamDto teamDto) {
    Team team = Team.builder().teamId(teamDto.getTeamId()).teamName(teamDto.getTeamName()).teamSportsId(teamDto.getSportId()).build();
    return teamRepositoryWrapper.addTeam(team);
  }
}
