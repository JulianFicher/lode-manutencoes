package com.lode.lodemanutencoes.service;

import com.lode.lodemanutencoes.model.Equipment;
import com.lode.lodemanutencoes.repository.EquipmentRepository;
import jakarta.validation.*;
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

    @Test
    void shouldSaveEquipmentSuccessfully() {
        Equipment equipmentuipment = new Equipment();
        equipmentuipment.setName("Tubulação Padrão");
        equipmentuipment.setType("Tubulacao");
        equipmentuipment.setStatus("Operacional");
        equipmentuipment.setInstallationDate(LocalDate.now());

        when(repository.save(any())).thenReturn(equipmentuipment);

        Equipment result = service.save(equipmentuipment);

        assertNotNull(result);
        assertEquals("Tubulação Padrão", result.getName());
        verify(repository, times(1)).save(equipmentuipment);
    }

    @Test
    void shouldFindEquipmentByName() {
        Equipment equipmentuipment = new Equipment();
        equipmentuipment.setName("Bomba");

        when(repository.findByName("bomba"))
                .thenReturn(List.of(equipmentuipment));

        List<Equipment> resultado = service.findByName("bomba");

        assertEquals(1, resultado.size());
        assertEquals("Bomba", resultado.get(0).getName());
    }

    @Test
    void shouldThrowExceptionWhenTryToSaveEquipmentWithoutName() {
        Equipment equipment = new Equipment();
        equipment.setName("");
        equipment.setType("Bomba");
        equipment.setStatus("Operacional");
        equipment.setInstallationDate(LocalDate.now());

        assertThrows(ConstraintViolationException.class, () -> {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<Equipment>> violations = validator.validate(equipment);
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }
        });
    }

    @Test
    void shouldThrowExceptionWhenNotFoundEquipment() {
        var notRegisteredId = 9999L;
        when(repository.findById(notRegisteredId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.findById(notRegisteredId));
    }
}
