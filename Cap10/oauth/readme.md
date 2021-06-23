# Cliente

_aircraft-positions_ actua de cliente. Usa la dependencia `spring-boot-starter-oauth2-client`.

En la configuración tenemos:

```yml
spring:
  security:
    oauth2:
      client:
        registration:
          okta:
            client-id: <your_assigned_client_id_here>
            client-secret: <your_assigned_client_secret_here>
        provider:
          okta:
            issuer-uri: https://<your_assigned_subdomain_here>.oktapreview.com/oauth2/default
```

Se indica una lista de proveedores OAuth. En caso de haber más de uno, la aplicación nos preguntará cual queremos utilizar para autenticar. Entre las propiedades tenemos el client secret y id, así como la url del emisor de tokens.

En la configuración creamos una bean destinada a realizar todas las llamadas REST. De esta forma aplicamos un cross-cutting concern, que en todas las llamadas se incluya el token OAuth

```ja
@Configuration
public class SecurityConfig {
    @Bean
    WebClient client(ClientRegistrationRepository regRepo,OAuth2AuthorizedClientRepository cliRepo) {
        ServletOAuth2AuthorizedClientExchangeFilterFunction filter =new ServletOAuth2AuthorizedClientExchangeFilterFunction(regRepo, cliRepo);
        filter.setDefaultOAuth2AuthorizedClient(true);

        return WebClient.builder()
                .baseUrl("http://localhost:7634/")
                .apply(filter.oauth2Configuration())
                .build();
    }
}
```

Usamos esta Bean para hacer las llamadas al servicio:

```java
@Component
public class PositionRetriever {
    private final AircraftRepository repository;
    private final WebClient client;

    public PositionRetriever(AircraftRepository repository, WebClient client) {
		super();
		this.repository = repository;
		this.client = client;
	}
```

# Resource

_planefinder_ hace las veces de recurso protegido. Incluye la dependencia `spring-boot-starter-oauth2-resource-server`. En las propiedades especificamos cual es el recurso que valida los tokens:

```yml
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: https://<your_assigned_subdomain_here>.oktapreview.com/oauth2/default
```

Configuramos el recurso - desde un punto de vista de la seguridad - añadiendo un filtro que especifica que ciertos recursos requieren una determinada claim. La claim la especificamos con `hasAuthority`.

```java
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange()
                .pathMatchers("/aircraft/**").hasAuthority("SCOPE_closedid")
                .pathMatchers("/aircraftadmin/**").hasAuthority("SCOPE_openid")
                .and().oauth2ResourceServer().jwt();

        return http.build();
    }
}
```


