# Cliente

Incluimos en _aircraft-positions_ la dependencia `spring-boot-starter-security`.

Usamos la anotación `@EnableWebSecurity` para definir la clase en la que configurar todas las beans relacionadas con la seguridad. Esta anotación incluye `@Confoguration` y `@EnableGlobalAuthentication`. Los principals que se van a utilizar se definen en `UserDetailsService`. En nuestro caso creamos un par de ususario y los cargamos en memoria:

```java
@Bean
    UserDetailsService authentication() {
        UserDetails peter = User.builder()
                .username("peter")
                .password(pwEncoder.encode("ppassword"))
                .roles("USER")
                .build();

        UserDetails jodie = User.builder()
                .username("jodie")
                .password(pwEncoder.encode("jpassword"))
                .roles("USER", "ADMIN")
                .build();

        System.out.println("   >>> Peter's password: " + peter.getPassword());
        System.out.println("   >>> Jodie's password: " + jodie.getPassword());

        return new InMemoryUserDetailsManager(peter, jodie);
    }
```

Destacar que entre los atributos de los usuarios se incluye el rol con `.roles("USER", "ADMIN")`. Otra cosa a destacar es que la contraseña tiene que especificarse condificada. El encoder codifica y encripta la contraseña:

```java
private final PasswordEncoder pwEncoder =PasswordEncoderFactories.createDelegatingPasswordEncoder();
```

Tenemos una factoria que nos permite usar diferentes encoders. Con `createDelegatingPasswordEncoder()` estamos usando el definido por defecto.

Por ultimo configuramos los filtros de seguridad. En este caso estamos protegiendo el recurso `aircraftadmin` para que sea accesible unicamente para el role `ADMIN`; El resto de recursos admiten que cualquiera que este autenticado pueda acceder:

```java
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/aircraftadmin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();
    }
```