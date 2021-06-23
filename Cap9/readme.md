- El contexto de aplicación que usaremos durante la prueba se especifica con la anotación `@SpringBootTest`. Por defecto esta anotación equivale a `@SpringBootTest(webEnvironment = WebEnvironment.MOCK)`, es decir, se creará un mock de un servidor web. Otras opciones son:

- `@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)`. Instancia un servidor web en un puerto aleatorio
- `@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)`. Instancia un servidor web en un puerto que se especifica en el properties
- `@SpringBootTest(webEnvironment = WebEnvironment.NONE)`. No Instancia un servidor web

- Si necesitamos hacer llamadas a APIs desde el caso de prueba, necesitamos incluir en el contexto de prueba. Esto inyecta en el contexto las beans necesarias para hacer llamadas a apis

```java
@SpringBootTest
@AutoConfigureWebTestClient
```

De esta forma podremos inyectar en nuestros casos de prueba el cliente:

```java
 @Test
    void getCurrentAircraftPositions(@Autowired WebTestClient client) {
        final Iterable<Aircraft> acPositions = client.get()
                .uri("/aircraft")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Aircraft.class)
                .returnResult()
                .getResponseBody();

        assertEquals(List.of(ac1, ac2), acPositions);
    }
```

Para hacer pruebas unitarias con MVC

```java
@SpringBootTest
@AutoConfigureMockMVC
```

- __Con `@WebFluxTest` equivale a `@SpringBootTest` más `@AutoConfigureWebTestClient`, entre otras cosas__. Podemos especificar una lista de controladores que serán cargados para su prueba.

```java
@WebFluxTest(controllers = {PositionController.class})
```

- Creamos un mock del servicio

```java
    @MockBean
    private PositionRetriever retriever;
```

Definimos el mock antes de que se ejecute cada test:

```java
    @BeforeEach
    void setUp(ApplicationContext context) {
        // Spring Airlines flight 001 en route, flying STL to SFO,
        //    at 30000' currently over Kansas City
        ac1 = new Aircraft(1L, "SAL001", "sqwk", "N12345", "SAL001",
                "STL-SFO", "LJ", "ct",
   
   (...)
   
        Mockito.when(retriever.retrieveAircraftPositions())
                .thenReturn(List.of(ac1, ac2));
    }
```

Es importante destacar que en estos tests no hemos usado el repositorio de datos, porque estamos mockeando el servicio.

- Test Slices. Cuando queremos probar repositorios, podemos usar unas anotaciones que harán lo siguiente:
	- Exploran todo el proyecto buscando clases anotadas con `@Entity`
	- Crean un repositorio para cada una de esas clases
	- Si se usa alguna base de datos embebida, como H2, la crean
	- En nuestro caso como estamos trabajando con repositorios JPA, usamos `@DataJpaTest`
	
Gracias a la anotación `@DataJpaTest` podemos incluir en nuestro test:

```java
    @Autowired
    private AircraftRepository repository;
```
	
Notese que antes y después de cada test preparamos los datos:

```java
    @BeforeEach
    void setUp() {
        // Spring Airlines flight 001 en route, flying STL to SFO,
        // at 30000' currently over Kansas City
        ac1 = new Aircraft(1L, "SAL001", "sqwk", "N12345", "SAL001",
                "STL-SFO", "LJ", "ct",
                30000, 280, 440, 0, 0,
                39.2979849, -94.71921, 0D, 0D, 0D,
                true, false,
                Instant.now(), Instant.now(), Instant.now());

        // Spring Airlines flight 002 en route, flying SFO to STL,
        // at 40000' currently over Denver
        ac2 = new Aircraft(2L, "SAL002", "sqwk", "N54321", "SAL002",
                "SFO-STL", "LJ", "ct",
                40000, 65, 440, 0, 0,
                39.8560963, -104.6759263, 0D, 0D, 0D,
                true, false,
                Instant.now(), Instant.now(), Instant.now());

        repository.saveAll(List.of(ac1, ac2));
    }
```

```java
    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }
```

En realidad en nuestro caso no haría falta borrar los datos después de cada test porque estamos usando una base de datos embebida, H2, y con cada test se destruye, y por lo tanto se limpia.