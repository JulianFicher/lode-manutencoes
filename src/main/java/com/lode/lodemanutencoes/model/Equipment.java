package com.lode.lodemanutencoes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Setter;

@Data
@Entity
@Table(name = "equipment")
public class Equipment {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The equipment name is required.")
    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "The type of equipment is mandatory.")
    private EquipmentType type;

    @NotNull(message = "Installation date is required.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate installationDate;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status is required.")
    private EquipmentStatus status;
}
