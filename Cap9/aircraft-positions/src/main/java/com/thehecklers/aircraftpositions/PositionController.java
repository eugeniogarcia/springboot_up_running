package com.thehecklers.aircraftpositions;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PositionController {
    private final PositionRetriever retriever;

    @GetMapping("/aircraft")
    public Iterable<Aircraft> getCurrentAircraftPositions() {
        return retriever.retrieveAircraftPositions();
    }

	public PositionController(PositionRetriever retriever) {
		super();
		this.retriever = retriever;
	}
    
    
}