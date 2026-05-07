package br.com.AutonomousAPI.controllers;

import br.com.AutonomousAPI.dtos.request.store.CreateStoreDTO;
import br.com.AutonomousAPI.dtos.response.store.StoreResponseDTO;
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

    @PostMapping
    public ResponseEntity<StoreResponseDTO> createStore(@RequestBody @Valid CreateStoreDTO dto) {
        StoreResponseDTO store = storeService.createStore(dto);
        return ResponseEntity.status(201).body(store);
    }

    @GetMapping
    public ResponseEntity<List<StoreResponseDTO>> findStores() {
        List<StoreResponseDTO> stores = storeService.findAll();
        return ResponseEntity.ok(stores);
    }
}
