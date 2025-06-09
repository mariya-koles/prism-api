package com.platform.prism.controller;

import com.platform.prism.dto.MedicationDto;
import com.platform.prism.model.Medication;
import com.platform.prism.service.MedicationService;
import com.platform.prism.util.mapper.MedicationMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@SecurityRequirement(name = "basicAuth")
@RequiredArgsConstructor
@RequestMapping("/medications")
public class MedicationController {

    private final MedicationService medicationService;

    private final MedicationMapper medicationMapper;

    /**
     * Retrieves a specific medication by its ID.
     *
     * @param id the ID of the medication to retrieve
     * @return the requested MedicationDto object, or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<MedicationDto> getMedicationById(@PathVariable Long id) {
        log.info("Fetching medication with ID: {}", id);
        try {
            MedicationDto medication = medicationService.getMedicationById(id);
            return ResponseEntity.ok(medication);
        } catch (RuntimeException e) {
            log.warn("Medication not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves all medications.
     *
     * @return ResponseEntity containing a list of all medications as DTOs
     */
    @GetMapping
    public ResponseEntity<List<MedicationDto>> getAllMedications() {
        log.info("Fetching all medications...");
        List<MedicationDto> medications = medicationService.getAllMedications().stream()
                .map(medicationMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(medications);
    }

    /**
     * Creates a new medication.
     *
     * @param dto the medication DTO
     * @return ResponseEntity with the created medication DTO
     */
    @PostMapping
    public ResponseEntity<MedicationDto> createMedication(@Valid @RequestBody MedicationDto dto) {
        log.info("Creating new medication: {}", dto.getProprietaryName());
        Medication entity = medicationMapper.toEntity(dto);
        Medication saved = medicationService.saveMedication(entity);
        MedicationDto responseDto = medicationMapper.toDto(saved);
        return ResponseEntity.status(201).body(responseDto);
    }

    /**
     * Updates an existing medication by ID.
     *
     * @param id  the ID of the medication to update
     * @param dto the updated medication DTO
     * @return ResponseEntity containing the updated medication DTO
     */
    @PutMapping("/{id}")
    public ResponseEntity<MedicationDto> updateMedication(@PathVariable Long id, @Valid @RequestBody MedicationDto dto) {
        log.info("Updating medication with ID: {}", id);
        try {
            MedicationDto updated = medicationService.updateMedication(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            log.warn("Failed to update: medication with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a medication by ID.
     *
     * @param id the ID of the medication to delete
     * @return ResponseEntity with deletion status
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedication(@PathVariable Long id) {
        log.info("Deleting medication with ID: {}", id);
        try {
            medicationService.deleteMedication(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.warn("Failed to delete: medication with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }
}
