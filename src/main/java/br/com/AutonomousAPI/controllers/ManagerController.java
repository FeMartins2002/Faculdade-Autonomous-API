package br.com.AutonomousAPI.controllers;

import br.com.AutonomousAPI.dtos.request.manager.LoginManagerDTO;
import br.com.AutonomousAPI.dtos.response.manager.ManagerResponseDTO;
import br.com.AutonomousAPI.services.ManagerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/autonomous/authentication")
public class ManagerController {
    @Autowired
    private ManagerService managerService;

    @PostMapping("/manager")
    public ResponseEntity<ManagerResponseDTO> login(@RequestBody @Valid LoginManagerDTO loginDTO) {
        ManagerResponseDTO response = managerService.login(loginDTO);
        return ResponseEntity.ok(response);
    }
}