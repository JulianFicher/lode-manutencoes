package com.lode.lodemanutencoes.service;

import com.lode.lodemanutencoes.model.Equipment;
import com.lode.lodemanutencoes.repository.EquipmentRepository;
import jakarta.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class EquipmentServiceTest {

    @Mock
    private EquipmentRepository repository;

    @InjectMocks
    private EquipmentService service;

    private Equipment equipment;

    @BeforeEach
    void setup() {
        equipment = new Equipment();
        equipment.setName("Painel");
        equipment.setType("Painel");
        equipment.setStatus("Manutenção Necessária");
        equipment.setInstallationDate(LocalDate.of(2026, 6, 10));
    }

    @Test
    void shouldSaveEquipmentSuccessfully() {
        when(repository.save(any())).thenReturn(equipment);

        Equipment result = service.save(equipment);

        assertNotNull(result);
        assertEquals("Painel", result.getName());
        verify(repository, times(1)).save(equipment);
    }

    @Test
    void shouldFindEquipmentByName() {
        equipment.setName("Bomba");

        when(repository.findByName("bomba"))
                .thenReturn(List.of(equipment));

        List<Equipment> result = service.findByName("bomba");

        assertEquals(1, result.size());
        assertEquals("Bomba", result.get(0).getName());
    }

    @Test
    void shouldUpdateEquipmentStatusSuccessfully() {
        equipment.setStatus("Manutenção Necessária");

        when(repository.findById(1L)).thenReturn(Optional.of(equipment));
        // retorna o objeto com status atualizado:
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Equipment result = service.update(1L, equipment);

        assertEquals("Manutenção Necessária", result.getStatus());
        verify(repository, times(1)).save(any());
    }

    @Test
    void shouldThrowExceptionWhenTryToSaveEquipmentWithoutName() {
        equipment.setName("");

        assertThrows(ConstraintViolationException.class, () -> {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<Equipment>> violations = validator.validate(equipment);
            boolean hasViolations = !violations.isEmpty();
            if (hasViolations) {
                throw new ConstraintViolationException(violations);
            }
        });
    }

    @Test
    void shouldThrowExceptionWhenNotFoundEquipmentById() {
        var notRegisteredId = 9999L;
        when(repository.findById(notRegisteredId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.findById(notRegisteredId));
    }
}
