package br.com.AutonomousAPI.controllers;

import br.com.AutonomousAPI.dtos.request.point.CreatePointDTO;
import br.com.AutonomousAPI.services.PointService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/autonomous/point")
public class PointController {
    @Autowired
    private PointService pointService;

    @PostMapping
    public ResponseEntity<Void> createPoint(@RequestBody @Valid CreatePointDTO dto) {
        pointService.savePoint(dto);
        return ResponseEntity.status(201).build();
    }
}
