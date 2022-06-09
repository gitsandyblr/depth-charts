package org.fanduel.depthcharts.api;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamDto {


  @NotNull
  private int teamId;

  @NotNull
  private String teamName;

  @NotNull
  private Integer sportId;

}
