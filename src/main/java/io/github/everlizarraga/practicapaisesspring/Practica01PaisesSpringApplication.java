package io.github.everlizarraga.practicapaisesspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication // = esta es una app Spring Boot, arrancá el framework
@EnableConfigurationProperties(PaisesProperties.class)
public class Practica01PaisesSpringApplication {

  public static void main(String[] args) {

    // Esta linea dice: "empezá a buscar clases desde acá"
    SpringApplication.run(Practica01PaisesSpringApplication.class, args);

    System.out.println("Hola SpringBoot !!!!");
  }

}
