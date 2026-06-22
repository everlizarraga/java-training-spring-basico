package io.github.everlizarraga.practicapaisesspring.PaisDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RespuestaPaises {
  @Getter @Setter
  private Data data;
}
