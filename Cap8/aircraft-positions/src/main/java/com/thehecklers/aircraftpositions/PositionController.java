package com.thehecklers.aircraftpositions;

import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

@Controller
public class PositionController {
    private final AircraftRepository repository;
    private final RSocketRequester requester;
    private WebClient client =
            WebClient.create("http://localhost:7634/aircraft");

    public PositionController(AircraftRepository repository,
                              RSocketRequester.Builder builder) {
        this.repository = repository;
        this.requester = builder.tcp("localhost", 7635);
    }

    // HTTP endpoint, HTTP requester (previously created)
    @GetMapping("/aircraft")
    public String getCurrentAircraftPositions(Model model) {
        Flux<Aircraft> aircraftFlux = repository.deleteAll() // Borra toda la información de Mongo
                .thenMany(client.get() // Llegados a este punto, esperamos a que las acciones anteriores se terminen antes de seguir
                        .retrieve() // Recupera datos
                        .bodyToFlux(Aircraft.class) // Convierte la respuesta a un Flux de Aircraft
                        .filter(plane -> !plane.getReg().isEmpty()) // Filtra los datos
                        .flatMap(repository::save)); // Guarda en Mongo

        //Actualiza el modelo, para que luevo en la vista se visualicen los datos
        model.addAttribute("currentPositions", aircraftFlux);
        return "positions";
    }

    // Usa un cliente RSocket para recuperar los datos
    // Devuelve un stream de datos
    @ResponseBody
    @GetMapping(value = "/acstream",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Aircraft> getCurrentACPositionsStream() {
        return requester.route("acstream") //Se conecta con el servidor de RSockets
                .data("Requesting aircraft positions") //Envia datos
                .retrieveFlux(Aircraft.class); //Convierte la información recibida en un flux de Aircraft
    }
}