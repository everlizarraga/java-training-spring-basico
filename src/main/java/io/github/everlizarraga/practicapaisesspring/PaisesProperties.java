package io.github.everlizarraga.practicapaisesspring;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "paises")
@Data
public class PaisesProperties {

  private String url;
  private String apiKey;
  private String nombreCatalogo;
}
