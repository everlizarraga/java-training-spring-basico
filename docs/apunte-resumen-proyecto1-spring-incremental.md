# 📑 Apunte-Resumen — Proyecto 1: Spring Boot Incremental (Etapas 0-7)

> **Para qué es este archivo:** repaso rápido para recuperar todo el Proyecto 1 sin releer los 8 documentos completos. Asume que **ya entendiste** cada tema en su momento — esto es la "recordadita". Si algo no te suena nada, andá al archivo de esa etapa.
>
> **Cómo usarlo:** leelo de corrido. Cada etapa es un bloque. El código es el mínimo para refrescar, no para aprender de cero.

---

## 🎯 El objetivo del proyecto (por si te perdiste)

Aprender **Spring Boot desde cero, bien**, construyendo una app incremental (una pieza por vez), en vez de que te tiren código terminado para descifrar. Dominio: países (consultar una API de países). Filosofía: **construir → correr → observar → entender → avanzar.**

---

## 🧠 Las 3 ideas-madre de todo Spring

Si te acordás de estas tres, te acordás del 80% del proyecto:

1. **Spring es un contenedor de objetos.** Crea y administra tus objetos por vos. Dejás de hacer `new`.
2. **Inversión de Control (IoC):** vos declarás qué clases querés; Spring decide cuándo y cómo crearlas.
3. **Inyección de Dependencias (DI):** cuando un objeto necesita otro, lo pide en el constructor y Spring se lo provee.

Todo lo demás (config, HTTP, tests) se construye sobre esto.

---

## Etapa 0 — Crear el proyecto

**Clic central:** un proyecto Spring Boot **ES un proyecto Maven** con Spring agregado. No es algo exótico.

**Lo que lo hace "Spring":**
- `<parent>` de Spring Boot en el `pom.xml` → heredás versiones de librerías ya probadas (por eso las dependencias no llevan `<version>`).
- **Starters** → dependencias "combo" que traen muchas otras juntas (ej: `spring-boot-starter` trae el núcleo).
- Clase `main` con `@SpringBootApplication` + `SpringApplication.run(...)`.
- `application.properties`/`.yml` para config.

**Formas de crear el proyecto:** Spring Initializr web (start.spring.io), plugin de IntelliJ, o a mano. Usamos Initializr. **Creamos sin dependencias extra** (pelado) a propósito, para ver Spring vacío.

**Stack:** Java 21 + Maven + Spring Boot 4.0.x + IntelliJ Community.

---

## Etapa 1 — El arranque

**Qué hiciste:** correr el Spring Boot vacío.

**Clic central:** la app **arrancó y terminó sola** (exit code 0). ¿Por qué? Porque **Spring NO es un servidor web.** Es un contenedor de objetos. Sin servidor web (que se agrega aparte), no hay nada que mantenga la app viva → termina.

**Mitos derribados:** "Spring = servidor que escucha en :8080" es **falso**. El servidor (Tomcat) es una pieza opcional.

**Piezas:**
- `SpringApplication.run(PaisesApplication.class, args)` → el botón de arranque. Crea el contenedor, busca clases, las instancia. **No bloquea** (el `main` sigue después).
- `@SpringBootApplication` → marca la clase como app Spring Boot. Enciende el escaneo de clases y la autoconfiguración. (No hace falta memorizar sus partes.)

---

## Etapa 2 — Beans (`@Component`)

**Qué hiciste:** una clase con `@Component` y un `println` en el constructor. Corriste y el mensaje apareció **sin que hicieras `new`**.

**Clic central:** **Spring hizo el `new` por vos.** Ese objeto que Spring crea y administra = un **bean**.

```java
@Component
public class CatalogoDePaises {
    public CatalogoDePaises() {
        System.out.println(">>> Spring me creó");  // aparece sin que hagas new
    }
}
```

**Lo que comprobaste:**
- Sin `@Component` → el mensaje NO aparece (Spring no la administra).
- Con dos `@Component` → ambos mensajes (Spring escanea TODO y crea todos).
- El mensaje aparece **una vez** → Spring crea **una sola instancia** por bean (singleton).

**Dónde busca Spring:** en el package de `PaisesApplication` y sus sub-packages. Por eso la clase main va en el package raíz.

**Primos de `@Component`** (mismo efecto, distinta semántica): `@Service`, `@Repository`, `@Controller`. Por ahora usás `@Component`.

---

## Etapa 3 — Inyección de dependencias

**Qué hiciste:** un bean que recibe otro bean por constructor. Spring los conectó solo.

**Clic central:** un bean **pide** lo que necesita en su constructor; **Spring se lo inyecta**. Vos no hacés `new` ni conectás nada.

```java
@Component
public class ServicioDePaises {
    private final CatalogoDePaises catalogo;
    public ServicioDePaises(CatalogoDePaises catalogo) {  // lo pide
        this.catalogo = catalogo;                          // Spring se lo da
    }
}
```

**Lo que comprobaste:**
- El bean necesitado se crea **primero** (Spring resuelve el orden solo).
- El **mismo `@hash`** aparece donde se inyecta → es el mismo objeto (singleton confirmado).
- Pedir algo que NO es bean (un `String`) → **error al arrancar**. Spring solo inyecta beans que conoce.

**`@Autowired`:** en código viejo se ponía para marcar la inyección. Hoy, con un solo constructor, **no hace falta**. (Excepción: en *tests*, sobre atributos, sí se usa.)

**Por qué la inyección por constructor es buena:** la clase no sabe cómo se construyen sus dependencias, es testeable sin Spring, y las dependencias quedan explícitas.

---

## Etapa 4 — `@Configuration` + `@Bean`

**Qué hiciste:** crear un bean de una clase que **no es tuya** (`Random` del JDK).

**Clic central:** `@Component` solo sirve para **tus** clases. Para clases ajenas (de librerías), usás un **método `@Bean`** dentro de una clase `@Configuration`.

```java
@Configuration
public class AppConfig {
    @Bean
    public Random random() {       // VOS hacés el new adentro
        return new Random();
    }
}
```

**Diferencia clave:**
| | `@Component` | `@Bean` |
|---|---|---|
| Va sobre | la clase | un método |
| El `new` lo hace | Spring | **vos** (en el método) |
| Para | tus clases | clases ajenas / construcción especial |

**Regla:** si podés ponerle `@Component`, ponéselo. Si no podés (no es tuya) o necesitás lógica de construcción, usá `@Bean`.

**Lo que comprobaste:** un bean de `@Bean` (Random) se inyecta **igual** que uno de `@Component`. Un bean es un bean, sin importar cómo se declaró. También es singleton.

---

## Etapa 5 — Configuración externa (`application.yml`)

**Qué hiciste:** poner valores en un archivo y leerlos desde el código.

**Clic central:** la config (URLs, tokens) va en un archivo **separado del código**, para cambiarla sin recompilar y no hardcodear secretos. Spring lee ese archivo y vuelca los valores en un objeto Java.

**El YAML:**
```yaml
paises:
    url: https://api.restcountries.com/countries/v5
    api-key: TU_TOKEN
```

**La clase que lo recibe (POJO de config):**
```java
@ConfigurationProperties(prefix = "paises")
@Data
public class PaisesProperties {
    private String url;
    private String apiKey;   // api-key (kebab) → apiKey (camel), Spring convierte
}
```

**Registro en la clase main:**
```java
@EnableConfigurationProperties(PaisesProperties.class)
```

**Cómo funciona el puente:** Spring lee `application.yml` al arrancar (automático, lo busca por el nombre en `resources/`) → crea `PaisesProperties` → llena los atributos con **los setters** → lo registra como bean → lo inyectás donde quieras.

**Cosas que sabés:**
- Si el YAML tiene **más** atributos que la clase → los ignora (no crash).
- Si la clase tiene **más** que el YAML → quedan `null`.
- `PaisesProperties` **necesita setters** (Spring los usa para asignar).
- Varias clases de config: **una sola** `@EnableConfigurationProperties({A.class, B.class})` con array, cada una pesca su sección por `prefix`.
- En `resources/` podés poner otros archivos (CSV, etc.); solo `application.yml` lo lee Spring solo.
- `@Component` + `@ConfigurationProperties` juntas es la forma alternativa (menos prolija); `@EnableConfigurationProperties` es la recomendada (mantiene el POJO limpio).

**YAML:** anidación = indentación (reemplaza `{}`), listas = guiones `-` (reemplaza `[]`). **Espacios, NUNCA tabs.**

---

## Etapa 6 — Llamada HTTP (`RestTemplate`) con token (API v5)

**Qué hiciste:** salir a internet, autenticarte con token, traer datos reales, convertir JSON en objetos.

> ⚠️ La API restcountries cambió: la v5 **requiere token** y devuelve estructura anidada (`data → objects`). URL base nueva: `https://api.restcountries.com/countries/v5`.

**Agregaste la dependencia web:**
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```
Trae: `RestTemplate`, Tomcat, **Jackson** (JSON). 

**Clic importante:** al agregar web, la app **YA NO termina** — Tomcat queda vivo en :8080. **Esto cierra el círculo de la Etapa 1** (el servidor mantiene la app viva).

**El cliente HTTP (clase ajena → `@Bean`):**
```java
@Bean
public RestTemplate restTemplate() {
    return new RestTemplate();
}
```

**La llamada con token** (no se puede con `getForObject` simple porque no permite headers; se usa `exchange`):
```java
public Pais buscarPorNombre(String nombre) {
    String url = properties.getUrl() + "?q=" + nombre;

    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(properties.getApiKey());   // "Authorization: Bearer <token>"
    HttpEntity<Void> request = new HttpEntity<>(headers);

    // ANTES (sin token): return restTemplate.getForObject(url, String.class);
    ResponseEntity<RespuestaPaises> response = restTemplate.exchange(
        url, HttpMethod.GET, request, RespuestaPaises.class);

    return response.getBody().getData().getObjects().get(0);
}
```

**Los DTOs (5 clases por la anidación):**
```
RespuestaPaises → data: Data → objects: List<Pais> → Pais
                                                       ├─ names: NombrePais (common, official)
                                                       ├─ capitals: List<Capital> (cada uno con name)
                                                       ├─ region, subregion, population
```
- Cada clase lleva `@JsonIgnoreProperties(ignoreUnknown = true)` → **ignora los ~80 campos que no mapeás**. Sin eso, Jackson rompería.
- **Regla profesional:** solo modelás los campos que vas a usar (mapeamos 6, no 80).
- `population` es `Long` (wrapper), no `long` → puede venir null de la API.
- `@JsonProperty("...")` solo si el nombre Java difiere del JSON (acá no hizo falta porque usamos los mismos nombres).

**Disparar la llamada:** `CommandLineRunner` (interfaz con `run()` que Spring ejecuta al terminar de arrancar; el lugar correcto para "hacer algo al inicio", no el constructor).
```java
@Component
public class Arranque implements CommandLineRunner {
    private final CatalogoDePaises catalogo;
    public Arranque(CatalogoDePaises catalogo) { this.catalogo = catalogo; }
    @Override
    public void run(String... args) {
        Pais arg = catalogo.buscarPorNombre("argentina");
        System.out.println(arg.getNames().getCommon());  // "Argentina"
    }
}
```

**Cómo el JSON se vuelve objetos:** `exchange(..., RespuestaPaises.class)` → Jackson lee el JSON y llena los objetos **con los setters** (igual que Spring con la config). Mismo concepto: texto estructurado → objeto Java vía setters.

**Seguridad:** el token va en `application.yml`, NUNCA hardcodeado ni en repos públicos. Si se filtra, se revoca y se regenera. Demo key `rc_live_demo` para probar sin gastar cuota.

**Detalle visto:** clase `Data` choca con `@Data` de Lombok → solución: usar `@Getter @Setter` en vez de `@Data` (o renombrar la clase). El aviso amarillo de `getBody()` posible null es advertencia, no error — en producción se chequea el null.

---

## Etapa 7 — Tests (`@SpringBootTest`)

**Qué hiciste:** testear la app con Spring levantado, reusando JUnit + AssertJ del Proyecto 0.

**Clic central:** `@SpringBootTest` **arranca todo el contexto de Spring** antes de los tests (crea beans, lee config). Por eso hasta un test vacío (`contextLoads()`) prueba algo: que la app **se puede ensamblar**.

**Las dependencias de test** ya venían en `spring-boot-starter-test` (JUnit, AssertJ, Mockito) — no agregaste nada.

**Test típico:**
```java
@SpringBootTest
class CatalogoDePaisesTest {
    @Autowired                          // en tests, @Autowired sobre atributo SÍ va
    private CatalogoDePaises catalogo;

    @Test
    void traeArgentina() {
        Pais arg = catalogo.buscarPorNombre("argentina");
        assertThat(arg.getNames().getCommon()).isEqualTo("Argentina");
        assertThat(arg.getCapitals().get(0).getName()).isEqualTo("Buenos Aires");
        assertThat(arg.getPopulation()).isPositive();   // NO valor exacto (cambia con el tiempo)
    }
}
```

**Reglas que sabés:**
- **`@Autowired` sobre atributo:** mal en producción, **bien en tests** (no hay constructor donde inyectar).
- **No ates tests a datos volátiles** (la población cambia → `.isPositive()`, no `.isEqualTo(46735004)`).
- **Costo de `@SpringBootTest`:** lento (arranca todo Spring). En proyectos grandes, pocos tests con Spring, muchos sin Spring.
- **Tests con Spring** (lentos, ensamblaje/flujos reales) **vs sin Spring** (rápidos, lógica aislada, creás objetos a mano con mocks). La pirámide: muchos sin Spring, pocos con Spring.
- El test de la API real depende de **internet + token** → es test de integración, frágil. En producción se mockea.

---

## 🗺️ Tabla de "qué annotation hace qué" (machete)

| Annotation | Qué hace | Dónde va |
|---|---|---|
| `@SpringBootApplication` | Marca la app, enciende escaneo + autoconfig | Clase main |
| `@Component` | "Spring, creá un bean de esta clase" | Tus clases |
| `@Configuration` | "Esta clase declara beans con métodos" | Clase de config |
| `@Bean` | "Ejecutá este método y guardá el resultado como bean" | Método (en `@Configuration`) |
| `@ConfigurationProperties(prefix=...)` | "Llená esta clase con esa sección del YAML" | POJO de config |
| `@EnableConfigurationProperties(X.class)` | "Activá y registrá esa clase de properties" | Clase main |
| `@JsonIgnoreProperties(ignoreUnknown=true)` | "Ignorá campos del JSON que no mapeo" | DTOs |
| `@JsonProperty("x")` | "Este campo Java = ese campo JSON" | Campo de DTO (si difieren los nombres) |
| `@SpringBootTest` | "Arrancá todo Spring antes de los tests" | Clase de test |
| `@Autowired` | Inyección (innecesaria en prod con 1 constructor; usada en tests) | Atributo de test |

---

## 🧭 Estado del proyecto

- **Hecho:** Etapas 0-5 completas. Etapa 6 codeada (llamada HTTP con token v5). 
- **Pendiente:** terminar de probar Etapa 6 y hacer Etapa 7 (tests).
- **Opcional/futuro:** endpoints propios (`@RestController`), manejo de errores HTTP, `UriComponentsBuilder` para URLs, mockear la API en tests, persistencia (JPA).

---

## 🎯 La frase que resume todo

> Spring crea y conecta tus objetos por vos (beans + inyección), los configura desde un archivo externo (`@ConfigurationProperties`), y te da herramientas para hablar con el mundo (HTTP) y testear (`@SpringBootTest`). Vos declarás **qué** querés; Spring se encarga del **cómo**.

---

**FIN DEL APUNTE-RESUMEN — Proyecto 1**

Si algún bloque no te refrescó lo suficiente, abrí el archivo de esa etapa (`proyecto1-etapaN-*.md`). Este resumen es el mapa; las etapas son el territorio detallado.
