Demuestra el uso de:

- Spring Data Streams
	- Supplier & Consumer
	- Configuración del driver y binder de Rabbit & Kafka, aunque en el ejemplo se usa el de Rabbit
- Aplicación MVC
- Webshockets. Crea un servidor de websockets. Se invoca al servidor desde el javascript de la página

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
