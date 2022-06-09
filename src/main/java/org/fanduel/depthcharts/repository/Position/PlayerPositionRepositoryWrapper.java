package org.fanduel.depthcharts.repository.Position;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PlayerPositionRepositoryWrapper {

  @Autowired
  PlayerPositionRepository repository;

  public Optional<PlayerPosition> getPlayerPositionByName(String positionName) {

    //sport id for NFL = 1
    return Optional.ofNullable(repository.findByPosition(positionName, 1));
  }
}
