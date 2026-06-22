package io.github.everlizarraga.practicapaisesspring;

import io.github.everlizarraga.practicapaisesspring.PaisDTO.Pais;
import io.github.everlizarraga.practicapaisesspring.PaisDTO.RespuestaPaises;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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
    //return restTemplate.getForObject(url, String.class);

    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(this.properties.getApiKey());

    HttpEntity<Void> request = new HttpEntity<>(headers);

    ResponseEntity<String> response = restTemplate.exchange(
        url,
        HttpMethod.GET,
        request,
        String.class
    );

    return response.getBody();
  }

  public List<Pais> buscarPais(String query) {
    String url = properties.getUrl() + "?q=" + query;

    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(this.properties.getApiKey());
    HttpEntity<Void> request = new HttpEntity<>(headers);
    ResponseEntity<RespuestaPaises> response = restTemplate.exchange(
        url,
        HttpMethod.GET,
        request,
        RespuestaPaises.class
    );

    RespuestaPaises body = response.getBody();
    if (body == null || body.getData() == null) {
      throw new RuntimeException("La API no devolvió datos");
    }
    return body         //RespuestaPaises
        .getData()      //Data
        .getObjects();  //List<Pais>

    /*
    RespuestaPaises          → el JSON completo // BODY
    └─ data: Data        → el "data" del JSON
         └─ objects: List<Pais>   → la lista de países
                └─ Pais            → cada país
                     ├─ names: NombrePais       → objeto anidado
                     ├─ capitals: List<Capital> → lista de objetos
                     ├─ region, subregion, population
    * */
  }

}
