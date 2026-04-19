package br.com.AutonomousAPI.controllers;

import br.com.AutonomousAPI.dtos.request.store.CreateStoreDTO;
import br.com.AutonomousAPI.dtos.response.StoreResponseDTO;
import br.com.AutonomousAPI.services.StoreService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/autonomous/store")
public class StoreController {
    @Autowired
    private StoreService storeService;

    @GetMapping
    public ResponseEntity<List<StoreResponseDTO>> findStores() {
        List<StoreResponseDTO> stores = storeService.findAll();
        return ResponseEntity.ok(stores);
    }

    @PostMapping
    public ResponseEntity<Void> createStore(@RequestBody @Valid CreateStoreDTO dto) {
        storeService.createStore(dto);
        return ResponseEntity.status(201).build();
    }
}
