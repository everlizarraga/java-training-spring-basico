package io.github.everlizarraga.practicapaisesspring;

import org.springframework.stereotype.Component;

@Component
public class Saludador {
  public Saludador() {
    System.out.println(">>> Spring creó un saludador");
  }
}
