package io.github.everlizarraga.practicapaisesspring;

import org.springframework.stereotype.Component;

@Component
public class CatalogoDePaises {

  private final PaisesProperties paisesProperties;

  public CatalogoDePaises(PaisesProperties paisesProperties) {
    this.paisesProperties = paisesProperties;
    System.out.println(">>> Spring creó una instancia de CatalogoPaiese !!!!");
    System.out.println("Config.url: " + paisesProperties.getUrl());
    System.out.println("Config.nombreCatalogo: " + paisesProperties.getNombreCatalogo());
  }
}
