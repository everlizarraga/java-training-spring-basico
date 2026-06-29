package io.github.everlizarraga.practicapaisesspring;

import io.github.everlizarraga.practicapaisesspring.PaisDTO.Pais;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Arranque implements CommandLineRunner {
  private final CatalogoDePaises catalogo;

  public Arranque(CatalogoDePaises catalogo) {
    this.catalogo = catalogo;
  }

  @Override
  public void run(String @NonNull ... args) {
    System.out.println(">>> Llamando a la API...");
    //this.traerArgentinaEnTextoPlano();
    this.buscarPorQuery("argentina");
    this.buscarPorQuery("chile");
    this.buscarPorQuery("japon");
  }

  private void traerArgentinaEnTextoPlano() {
    String stringArgentina = this.catalogo.traerArgentinaComoTexto();
    System.out.println(">>> " + stringArgentina);
  }

  private void buscarPorQuery(String query) {
    List<Pais> paises = this.catalogo.buscarPais(query);
    System.out.println(":::::: Búsqueda para: " + query + " ::::::");
    System.out.println(paises);
    paises.forEach(p -> {
      System.out.println("---------");
      System.out.println(">>> País traído: " + p.getNombres().getComun());
      System.out.println(">>> Nombre oficial: " + p.getNombres().getOficial());
      System.out.println(">>> Capital: " + p.getCapitales().get(0).getNombre());
      System.out.println(">>> Región: " + p.getRegion());
      System.out.println(">>> Subregión: " + p.getSubregion());
      System.out.println(">>> Población: " + p.getPoblacion());
    });
  }
}
