package br.com.AutonomousAPI.controllers;

import br.com.AutonomousAPI.dtos.request.freelancer.CreateFreelancerDTO;
import br.com.AutonomousAPI.services.FreelancerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/autonomous/freelancers")
public class FreelancerController {
    @Autowired
    private FreelancerService freelancerService;

    @PostMapping
    public ResponseEntity<Void> createFreelancer(@RequestBody @Valid CreateFreelancerDTO dto) {
        freelancerService.createFreelancer(dto);
        return ResponseEntity.status(201).build();
    }
}
