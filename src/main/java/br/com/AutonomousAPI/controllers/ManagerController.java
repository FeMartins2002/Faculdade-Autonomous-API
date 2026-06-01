package br.com.AutonomousAPI.controllers;

import br.com.AutonomousAPI.dtos.request.manager.*;
import br.com.AutonomousAPI.dtos.response.manager.*;
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

    @PostMapping("/password")
    public ResponseEntity<ManagerResponseDTO> changePassword(@RequestBody @Valid ChangePasswordDTO dto) {
        ManagerResponseDTO response = managerService.changePassword(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/token")
    public ResponseEntity<TokenResponse> changePassword(@RequestParam String email) {
        TokenResponse token = managerService.validateToken(email);
        return ResponseEntity.ok(token);
    }
}
