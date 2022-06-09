package org.fanduel.depthcharts.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayerDto {

  private String playerPosition;
  private String playerName;
  private Integer playerPositionDepth;
  private Integer playerNumber;

}
