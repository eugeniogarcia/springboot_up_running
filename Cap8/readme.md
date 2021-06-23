Convierte en reactive los proyectos del capitulo 7.

Demuestra el uso de 
- Se usa también H2
- En _aircraft-positions_ tenemos 
	- por un lado una aplicación MVC, 
	- por otro lado dos end-points Rest. Uno de los endpoints está configurado como un streaming endpoint
	- Hacemos uso de RSockets. La aplicación se conecta con un cliente RSocket para recuperar los datos que luego expone en el streaming endpoint
	- Usamos el mongo embeded para almacenar los datos. El mongo embebido habitualmente se usa para testear, pero le hemos quitado el scope "test" de la dependencia, y lo usamos para probar (el puerto es 1065). Vamos ha acceder a los datos de forma reactiva, para lo cual usamos el driver _spring-boot-starter-data-mongodb-reactive_

- En _planefinder_ tenemos 
	- Usamos una base de datos H2 embeded. Accederemos a los datos de forma reactiva, para lo que usamos el driver _r2dbc-h2_
	- La librería reactiva esta menos evolucionada, así que tenemos que hacer algunas cosas manualmente. En _DbConxInit_ inicializamos la base de datos, _ConnectionFactoryInitializer_, especificando el esquema que queremos que se construya para acceder a los datos. Populamos también los datos de prueba usando un _CommandLineRunner_
	- Expone un servidor RSocket

# Instala

## Rabbit MQ

[Instalar Rabbit MQ](https://hub.docker.com/_/rabbitmq)

Sin el plugin de gestión

Con el plugin de gestión:

```ps
docker pull rabbitmq:management
```

Ejecuta Rabbit:

```ps
docker run -d --hostname my-rabbit --name some-rabbit  -p 5672:5672 -p 8090:15672 rabbitmq:management
```

El usuario y contraseña para entrar en la aplicación de gestión es el usuario _guest_ y contraseña _guest_.

## Spring

Especificamos un Supplier
```java
	@Bean
    Supplier<Iterable<Aircraft>> reportPositions() {
        return () -> {
            try {

```

```yml
spring.cloud.stream.bindings.reportPositions-out-0.destination=aircraftpositions
spring.cloud.stream.bindings.reportPositions-out-0.binder=rabbit
```
