package com.lode.lodemanutencoes.service;

import com.lode.lodemanutencoes.model.Equipment;
import com.lode.lodemanutencoes.repository.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipmentService {

    private final EquipmentRepository repository;

    public List<Equipment> findAll() {
        return repository.findAll();
    }

    public List<Equipment> findByName(String name) {
        return repository.findByName(name);
    }

    public Equipment findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipment id " + id + " not found."));
    }

    public Equipment save(Equipment equipment) {
        return repository.save(equipment);
    }

    public Equipment update(Long id, Equipment data) {
        Equipment registeredEquipment = findById(id);
        registeredEquipment.setName(data.getName());
        registeredEquipment.setType(data.getType());
        registeredEquipment.setInstallationDate(data.getInstallationDate());
        registeredEquipment.setStatus(data.getStatus());
        return repository.save(registeredEquipment);
    }

    public void delete(Long id) {
        findById(id);
        repository.deleteById(id);
    }
}
