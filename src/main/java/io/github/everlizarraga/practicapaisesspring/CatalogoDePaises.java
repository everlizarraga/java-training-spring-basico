package io.github.everlizarraga.practicapaisesspring;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CatalogoDePaises {

  private final PaisesProperties properties;
  private final RestTemplate restTemplate;

  public CatalogoDePaises(RestTemplate restTemplate, PaisesProperties properties) {
    this.restTemplate = restTemplate;
    this.properties = properties;
    System.out.println("> Config.url: " + properties.getUrl());
  }

  public String traerArgentinaComoTexto() {
    String url = this.properties.getUrl() + "?q=argentina";
    return restTemplate.getForObject(url, String.class);

  }
}
