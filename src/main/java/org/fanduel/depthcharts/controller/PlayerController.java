package org.fanduel.depthcharts.controller;

import com.sun.istack.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.fanduel.depthcharts.api.PlayerDto;
import org.fanduel.depthcharts.api.ResponseDto;
import org.fanduel.depthcharts.exception.DepthChartException;
import org.fanduel.depthcharts.repository.player.Player;
import org.fanduel.depthcharts.service.PlayerDepthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/v1")
@Slf4j
public class PlayerController {

  @Autowired
  PlayerDepthService service;

  @PostMapping(path = "/addPlayerToDepthChart")
  public ResponseEntity<ResponseDto> addPlayerToDepthChart(@Validated @NotNull @RequestBody PlayerDto playerDto) {
    log.info("Adding player to depth chart");
    Player player = null;
    try {
      if (playerDto == null) {
        return new ResponseEntity<>(
          ResponseDto.builder().message("Provide player name, position, depth values in request").build(), HttpStatus.BAD_REQUEST);
      } else if(StringUtils.isEmpty(playerDto.getPlayerPosition()) || StringUtils.isEmpty(playerDto.getPlayerName())
      || StringUtils.isEmpty(playerDto.getPlayerPositionDepth().toString())){
      return new ResponseEntity<>(
        ResponseDto.builder().message("Provide player name, position, depth values in request").build(), HttpStatus.BAD_REQUEST);
    }

    player = service.addPlayerToDepthChart(playerDto);
    } catch (DepthChartException e) {
      return new ResponseEntity<>(
        ResponseDto.builder().message("exception while adding "+player.getPlayerName()+" to the depth chart").build(), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(
      ResponseDto.builder().message(player.getPlayerName() + " added to the depth chart").build(), HttpStatus.OK);
  }

  @DeleteMapping(path = "/removePlayerToDepthChart")
  public ResponseEntity<ResponseDto> removePlayerToDepthChart(@Validated @RequestBody PlayerDto playerDto) {
    log.info("Removing player from depth chart");
    Player player = null;
    try {
      if (validateRequest(playerDto)) return new ResponseEntity<>(
        ResponseDto.builder().message("Provide player name, position values in request").build(), HttpStatus.BAD_REQUEST);
      player = service.removePlayerToDepthChart(playerDto);
    } catch (DepthChartException e) {
      return new ResponseEntity<>(
        ResponseDto.builder().message("exception while removing "+player.getPlayerName()+" from the depth chart").build(), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(
      ResponseDto.builder().message("# "+player.getPlayerId()+" - "+player.getPlayerName()).build(), HttpStatus.OK);
  }

  @GetMapping(path = "/getFullDepthChart")
  public ResponseEntity<List<PlayerDto>> getFullDepthChart() {
    log.info("getting full depth chart");
    List<PlayerDto> playerDtos = service.getFullDepthChart();
    return new ResponseEntity<>(playerDtos, HttpStatus.OK);
  }

  @PostMapping(path = "/getBackups")
  public ResponseEntity<?> getBackups(@Validated @RequestBody PlayerDto playerDto) {
    log.info("getting backups");
    List<PlayerDto> playerDtos;
    try {
      if (validateRequest(playerDto)) return new ResponseEntity<>(
        ResponseDto.builder().message("Provide player name, position values in request").build(), HttpStatus.BAD_REQUEST);
      playerDtos = service.getBackups(playerDto.getPlayerPosition(), playerDto.getPlayerName());
    } catch (DepthChartException e) {
      return new ResponseEntity<>(
        ResponseDto.builder().message(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(playerDtos, HttpStatus.OK);
  }

  private boolean validateRequest(@RequestBody @Validated PlayerDto playerDto) {
    if (playerDto == null) {
      return true;
    } else if (StringUtils.isEmpty(playerDto.getPlayerPosition()) || StringUtils.isEmpty(playerDto.getPlayerName())) {
      return true;
    }
    return false;
  }

}
