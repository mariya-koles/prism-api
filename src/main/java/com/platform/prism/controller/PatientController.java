package com.platform.prism.controller;

import com.platform.prism.dto.PatientDto;
import com.platform.prism.model.Patient;
import com.platform.prism.service.PatientService;
import com.platform.prism.util.mapper.PatientMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;
    private final PatientMapper patientMapper;

    /**
     * Retrieves a specific patient by their database ID.
     *
     * @param id the ID of the patient to retrieve
     * @return the requested PatientDto object, or 404 if not found
     * @HTTP 200 OK if patient is found, 404 Not Found otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<PatientDto> getPatientById(@PathVariable Long id) {
        log.info("Fetching patient with ID: {}", id);
        try {
            PatientDto patient = patientService.getPatientById(id);
            return ResponseEntity.ok(patient);
        } catch (RuntimeException e) {
            log.warn("Patient not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves all patients.
     *
     * @return ResponseEntity containing a list of all patients as DTOs
     * @HTTP 200 OK always
     */
    @GetMapping
    public ResponseEntity<List<PatientDto>> getAllPatients() {
        log.info("Fetching all patients...");
        List<PatientDto> patients = patientService.getAllPatients().stream()
                .map(patientMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(patients);
    }

    /**
     * Creates a new patient.
     *
     * @param dto the patient data transfer object containing patient details
     * @return ResponseEntity with the created patient DTO
     * @HTTP 201 Created if creation is successful
     */
    @PostMapping
    public ResponseEntity<PatientDto> createPatient(@Valid @RequestBody PatientDto dto) {
        log.info("Creating new patient: {} {}", dto.getFirstName(), dto.getLastName());
        Patient entity = patientMapper.toEntity(dto);
        Patient saved = patientService.savePatient(entity);
        PatientDto responseDto = patientMapper.toDto(saved);
        return ResponseEntity.status(201).body(responseDto);
    }

    /**
     * Updates an existing patient by ID.
     *
     * @param id    the ID of the patient to update
     * @param dto   the updated patient data
     * @return ResponseEntity containing the updated patient DTO
     * @HTTP 200 OK if update is successful, 404 Not Found if patient is missing
     */
    @PutMapping("/{id}")
    public ResponseEntity<PatientDto> updatePatient(@PathVariable Long id, @Valid @RequestBody PatientDto dto) {
        log.info("Updating patient with ID: {}", id);
        try {
            PatientDto updated = patientService.updatePatient(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            log.warn("Failed to update: patient with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a patient by ID.
     *
     * @param id the ID of the patient to delete
     * @return ResponseEntity with deletion status
     * @HTTP 204 No Content if deletion is successful, 404 Not Found otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        log.info("Deleting patient with ID: {}", id);
        try {
            patientService.deletePatient(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.warn("Failed to delete: patient with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }
}
