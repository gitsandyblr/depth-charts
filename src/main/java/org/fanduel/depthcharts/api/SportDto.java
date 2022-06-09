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
public class SportDto {

  @NotNull
  private int sportId;

  @NotNull
  private String sportName;

}
