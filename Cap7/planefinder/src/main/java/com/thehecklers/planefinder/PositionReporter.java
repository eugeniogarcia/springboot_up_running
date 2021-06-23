package com.thehecklers.planefinder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;

@Configuration
public class PositionReporter {
    private final PlaneFinderService pfService;

    public PositionReporter(PlaneFinderService pfService) {
		super();
		this.pfService = pfService;
	}

	@Bean
    Supplier<Iterable<Aircraft>> reportPositions() {
        return () -> {
            try {
                return pfService.getAircraft();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return List.of();
        };
    }
}