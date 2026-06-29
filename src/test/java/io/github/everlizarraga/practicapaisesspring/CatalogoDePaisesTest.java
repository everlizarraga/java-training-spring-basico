package io.github.everlizarraga.practicapaisesspring;

import io.github.everlizarraga.practicapaisesspring.PaisDTO.Pais;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest // Arranca el contexto de Spring antes de los tests.
public class CatalogoDePaisesTest {

  @Autowired // Le pide a spring que inyecte el bean.
  private CatalogoDePaises catalogo;

  @Test
  void elCatalogoSeInyectaCorrectamente() {
    assertThat(catalogo).isNotNull();
  }

  @Test
  void buscarArgentinaTraeLosDatosCorrectos() {
    List<Pais> paises = catalogo.buscarPais("Argentina");
    Pais argentina = paises.getFirst();

    assertThat(argentina).isNotNull();

    // Navegamos la estructura v5: el nombre está en names.common
    assertThat(argentina.getNombres().getComun()).isEqualTo("Argentina");
    assertThat(argentina.getNombres().getOficial()).isEqualTo("Argentine Republic");

    // La región
    assertThat(argentina.getRegion()).isEqualTo("Americas");
    assertThat(argentina.getSubregion()).isEqualTo("South America");

    // La capital ahora es una lista de objetos Capital; sacamos el name del primero
    assertThat(argentina.getCapitales().getFirst().getNombre()).isEqualTo("Buenos Aires");

    // La población es un número grande; verificamos que sea positivo
    // (no ponemos el valor exacto porque la población cambia con el tiempo)
    assertThat(argentina.getPoblacion()).isPositive();
  }
}
