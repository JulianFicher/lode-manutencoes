package com.lode.lodemanutencoes.model;

import lombok.Getter;

@Getter
public enum EquipmentStatus {
    OPERACIONAL("Operacional"),
    MANUTENCAO_NECESSARIA("Manutenção Necessária"),
    CRITICO("Crítico");

    private final String description;

    EquipmentStatus(String description) {
        this.description = description;
    }

}
