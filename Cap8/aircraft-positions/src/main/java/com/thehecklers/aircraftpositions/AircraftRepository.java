package com.thehecklers.aircraftpositions;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

//Repositorio Reactive
public interface AircraftRepository extends ReactiveCrudRepository<Aircraft, Long> {}
