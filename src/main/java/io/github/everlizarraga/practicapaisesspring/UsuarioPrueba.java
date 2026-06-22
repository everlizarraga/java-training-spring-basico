package io.github.everlizarraga.practicapaisesspring;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "usuario")
@Data
public class UsuarioPrueba {

  private String nombre;
  private String apellido;
}
