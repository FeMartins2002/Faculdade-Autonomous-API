package br.com.AutonomousAPI.controllers;

import br.com.AutonomousAPI.dtos.request.freelancer.*;
import br.com.AutonomousAPI.dtos.request.manager.ChangePasswordDTO;
import br.com.AutonomousAPI.dtos.response.freelancer.*;
import br.com.AutonomousAPI.services.FreelancerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/autonomous/freelancer")
public class FreelancerController {
    @Autowired
    private FreelancerService freelancerService;

    @PostMapping("/login")
    public ResponseEntity<FreelancerResponseDTO> login(@RequestBody @Valid LoginFreelancerDTO loginDTO) {
        FreelancerResponseDTO response = freelancerService.login(loginDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/password")
    public ResponseEntity<FreelancerResponseDTO> chagePassword(@RequestBody @Valid ChangePasswordDTO dto) {
        FreelancerResponseDTO response = freelancerService.changePassword(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<FreelancerResponseDTO> createFreelancer(@RequestBody @Valid CreateFreelancerDTO dto) {
        FreelancerResponseDTO freelancer = freelancerService.createFreelancer(dto);
        return ResponseEntity.status(201).body(freelancer);
    }

    @PutMapping
    public ResponseEntity<FreelancerResponseDTO> updateFreelancer(@RequestBody @Valid UpdateFreelancerDTO dto) {
        FreelancerResponseDTO freelancer = freelancerService.updateFreelancer(dto);
        return ResponseEntity.status(200).body(freelancer);
    }

    @DeleteMapping
    public ResponseEntity<FreelancerResponseDTO> deleteFreelancer(@RequestBody @Valid DeleteFreelancerDTO dto) {
        FreelancerResponseDTO freelancer = freelancerService.deleteFreelancer(dto);
        return ResponseEntity.ok().body(freelancer);
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
