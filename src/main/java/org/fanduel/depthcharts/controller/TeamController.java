package org.fanduel.depthcharts.controller;

import lombok.extern.slf4j.Slf4j;
import org.fanduel.depthcharts.api.ResponseDto;
import org.fanduel.depthcharts.api.TeamDto;
import org.fanduel.depthcharts.repository.team.Team;
import org.fanduel.depthcharts.service.DepthChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1")
@Slf4j
public class TeamController {

  @Autowired
  DepthChartService service;

  @PostMapping(path = "/addTeam")
  public ResponseEntity<ResponseDto> addTeam(@Validated @RequestBody TeamDto teamDto) {
    log.info("Adding a new team to the database");
    Team team = service.addTeam(teamDto);
    return new ResponseEntity<>(
      ResponseDto.builder().message("Team " + team.getTeamName() + " added to the database").build(), HttpStatus.OK);
  }
}
