package io.github.everlizarraga.practicapaisesspring.PaisDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@Data
public class NombrePais {
  @JsonProperty("common")
  private String comun;

  @JsonProperty("official")
  private String oficial;
}
