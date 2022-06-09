package org.fanduel.depthcharts.repository.Position;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface PlayerPositionRepository extends JpaRepository<PlayerPosition, Long> {

  @Query("select pp from PlayerPosition pp where pp.playerPositionName = :positionName and pp.playerTeamSportsId = :sportId")
  PlayerPosition findByPosition(@Param("positionName") String positionName, @Param("sportId") int sportId);

}
