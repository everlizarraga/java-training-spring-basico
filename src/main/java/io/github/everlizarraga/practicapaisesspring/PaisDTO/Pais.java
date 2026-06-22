package io.github.everlizarraga.practicapaisesspring.PaisDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@Data
public class Pais {
  @JsonProperty("names")
  private NombrePais nombres;
  @JsonProperty("capitals")
  private List<Capital> capitales;
  private String region;
  private String subregion;
  @JsonProperty("population")
  private Long poblacion;
}
