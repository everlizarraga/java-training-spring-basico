package io.github.everlizarraga.practicapaisesspring;

import org.springframework.stereotype.Component;

@Component
public class Saludador {
  private final UsuarioPrueba usuarioPrueba;

  public Saludador(UsuarioPrueba usuarioPrueba) {
    this.usuarioPrueba = usuarioPrueba;
    System.out.println(">>> Spring creó un saludador");
    System.out.println("<><><> UsuarioPrueba: " + usuarioPrueba.getNombre() + " " + usuarioPrueba.getApellido());
  }
}
