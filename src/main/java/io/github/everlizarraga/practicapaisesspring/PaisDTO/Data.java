package io.github.everlizarraga.practicapaisesspring.PaisDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {
  @Getter @Setter
  private List<Pais> objects;
}
