package br.com.AutonomousAPI.controllers;

import br.com.AutonomousAPI.dtos.request.freelancer.CreateFreelancerDTO;
import br.com.AutonomousAPI.dtos.response.freelancer.FreelancerOptionDTO;
import br.com.AutonomousAPI.dtos.response.freelancer.FreelancerResponseDTO;
import br.com.AutonomousAPI.services.FreelancerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/autonomous/freelancer")
public class FreelancerController {
    @Autowired
    private FreelancerService freelancerService;

    @PostMapping
    public ResponseEntity<FreelancerResponseDTO> createFreelancer(@RequestBody @Valid CreateFreelancerDTO dto) {
        FreelancerResponseDTO freelancer = freelancerService.createFreelancer(dto);
        return ResponseEntity.status(201).body(freelancer);
    }

    @GetMapping(value = "/actives")
    public ResponseEntity<List<FreelancerResponseDTO>> freelancersActives() {
        List<FreelancerResponseDTO> freelancers = freelancerService.freelancersActives();
        return ResponseEntity.ok().body(freelancers);
    }

    @GetMapping(value = "/inactives")
    public ResponseEntity<List<FreelancerResponseDTO>> freelancersInactives() {
        List<FreelancerResponseDTO> freelancers = freelancerService.freelancersInactives();
        return ResponseEntity.ok().body(freelancers);
    }

    @GetMapping(value = "/options")
    public ResponseEntity<List<FreelancerOptionDTO>> freelancersOptions() {
        List<FreelancerOptionDTO> freelancers = freelancerService.freelancerOptions();
        return ResponseEntity.ok().body(freelancers);
    }
}
