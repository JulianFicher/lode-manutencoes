package com.lode.lodemanutencoes.exception;

public class EquipmentNotFoundException extends RuntimeException {
    public EquipmentNotFoundException(Long id) {
        super("Equipamento de id " + id + " não encontrado.");
    }
}
