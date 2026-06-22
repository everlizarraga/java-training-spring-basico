package io.github.everlizarraga.practicapaisesspring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@Configuration
public class AppConfig {

  @Bean
  public Random random() {
    return new Random();
  }

  @Bean
  public RestTemplate restTemplate() {
    System.out.println(">>> Se creó el RestTempleate");
    return new RestTemplate();
  }
}
