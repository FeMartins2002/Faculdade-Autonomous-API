package br.com.AutonomousAPI.controllers;

import br.com.AutonomousAPI.dtos.request.scale.*;
import br.com.AutonomousAPI.dtos.response.scale.*;
import br.com.AutonomousAPI.enums.ScaleStatus;
import br.com.AutonomousAPI.services.ScaleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/autonomous/scale")
public class ScaleController {
    @Autowired
    private ScaleService scaleService;

    @PostMapping
    public ResponseEntity<ScaleResponseDTO> createScale(@RequestBody @Valid CreateScaleDTO dto) {
        ScaleResponseDTO scale = scaleService.createScale(dto);
        return ResponseEntity.status(201).body(scale);
    }

    @PutMapping
    public ResponseEntity<ScaleResponseDTO> updateScale(@RequestBody @Valid UpdateScaleDTO dto) {
        ScaleResponseDTO scale = scaleService.updateScale(dto);
        return ResponseEntity.status(200).body(scale);
    }

    @PatchMapping("/completed")
    public ResponseEntity<ScaleResponseDTO> completedScale(@RequestBody @Valid CompletedScaleDTO dto) {
        ScaleResponseDTO scale = scaleService.completedScale(dto);
        return ResponseEntity.ok(scale);
    }

    @PatchMapping("/cancelled")
    public ResponseEntity<ScaleResponseDTO> cancelledScale(@RequestBody @Valid CancelledScaleDTO dto) {
        ScaleResponseDTO scale = scaleService.cancelledScale(dto);
        return ResponseEntity.ok(scale);
    }

    @GetMapping
    public ResponseEntity<List<ScaleResponseDTO>> getScalesByStatus(@RequestParam ScaleStatus status) {
        List<ScaleResponseDTO> scales = scaleService.findByStatus(status);
        return ResponseEntity.ok(scales);
    }

    @GetMapping("/closed")
    public ResponseEntity<List<ScaleResponseDTO>> getAllScales() {
        List<ScaleResponseDTO> scales = scaleService.findByScalesClosed();
        return ResponseEntity.ok(scales);
    }

    @GetMapping("/observation/{id}")
    public ResponseEntity<ObservationResponse> getScaleObservation(@PathVariable Long id) {
        ObservationResponse observation = scaleService.findObservationById(id);
        return ResponseEntity.ok(observation);
    }
}
