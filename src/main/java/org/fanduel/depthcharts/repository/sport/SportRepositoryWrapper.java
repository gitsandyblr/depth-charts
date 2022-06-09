package org.fanduel.depthcharts.repository.sport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class SportRepositoryWrapper {

  @Autowired
  SportRepository repository;

  public Sport addSport(Sport sport) {
    return repository.save(sport);
  }

}
