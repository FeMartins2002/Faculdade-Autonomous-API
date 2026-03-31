package br.com.AutonomousAPI.controllers;

import br.com.AutonomousAPI.dtos.request.scale.CreateScaleDTO;
import br.com.AutonomousAPI.dtos.response.scale.ScaleResponseDTO;
import br.com.AutonomousAPI.enums.ScaleStatus;
import br.com.AutonomousAPI.services.ScaleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/autonomous/scale")
public class ScaleController {
    @Autowired
    private ScaleService scaleService;

    @PostMapping
    public ResponseEntity<Void> createScale(@RequestBody @Valid CreateScaleDTO createScaleDTO) {
        scaleService.createScale(createScaleDTO);
        return ResponseEntity.status(201).build();
    }

    @GetMapping
    public ResponseEntity<List<ScaleResponseDTO>> getScalesByStatus(@RequestParam ScaleStatus status) {
        List<ScaleResponseDTO> scales = scaleService.findByStatus(status);
        return ResponseEntity.ok(scales);
    }
}
