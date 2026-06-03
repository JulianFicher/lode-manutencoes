package com.lode.lodemanutencoes.controller;

import com.lode.lodemanutencoes.model.Equipment;
import com.lode.lodemanutencoes.service.EquipmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/equipamentos")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class EquipmentController {

    private final EquipmentService service;

    // GET /api/equipamentos ou /api/equipamentos?name=bomba
    @GetMapping
    public List<Equipment> findAll(@RequestParam(required = false) String name) {
        if (name != null && !name.isBlank()) {
            return service.findByName(name);
        }
        return service.findAll();
    }

    // GET /api/equipamentos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Equipment> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    // POST /api/equipamentos
    @PostMapping
    public ResponseEntity<Equipment> create(@Valid @RequestBody Equipment equipment) {
        return ResponseEntity.status(201).body(service.save(equipment));
    }

    // PUT /api/equipamentos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Equipment> update(
            @PathVariable Long id,
            @Valid @RequestBody Equipment equipment) {
        return ResponseEntity.ok(service.update(id, equipment));
    }

    // DELETE /api/equipamentos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
