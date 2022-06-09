package org.fanduel.depthcharts.controller;

import lombok.extern.slf4j.Slf4j;
import org.fanduel.depthcharts.api.ResponseDto;
import org.fanduel.depthcharts.api.SportDto;
import org.fanduel.depthcharts.repository.sport.Sport;
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
public class SportController {

  @Autowired
  DepthChartService service;

  @PostMapping(path = "/addSport")
  public ResponseEntity<ResponseDto> addSport(@Validated @RequestBody SportDto sportDto) {
    log.info("Adding a new sport to the database");
    Sport sport = service.addSport(sportDto);
    return new ResponseEntity<>(
      ResponseDto.builder().message("sport " + sport.getSportName() + " added to the database").build(), HttpStatus.OK);
  }
}
