package com.thehecklers.aircraftpositions;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PositionController {
    @NonNull
    private final AircraftRepository repository;

    public PositionController(AircraftRepository repository) {
		super();
		this.repository = repository;
	}

    //Controlador de la página positions
	@GetMapping("/aircraft")
    public String getCurrentAircraftPositions(Model model) {
        model.addAttribute("currentPositions", repository.findAll());
        return "positions";
    }
}
