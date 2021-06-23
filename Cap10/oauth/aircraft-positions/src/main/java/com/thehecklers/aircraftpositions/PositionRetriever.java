package com.thehecklers.aircraftpositions;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class PositionRetriever {
    private final AircraftRepository repository;
    private final WebClient client;

    public PositionRetriever(AircraftRepository repository, WebClient client) {
		super();
		this.repository = repository;
		this.client = client;
	}

	Iterable<Aircraft> retrieveAircraftPositions(String endpoint) {
        repository.deleteAll();

        client.get()
                .uri((null != endpoint) ? endpoint : "")
                .retrieve()
                .bodyToFlux(Aircraft.class)
                .filter(ac -> !ac.getReg().isEmpty())
                .toStream()
                .forEach(repository::save);

        return repository.findAll();
    }
}
