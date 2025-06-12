package com.platform.prism.controller;

import com.platform.prism.dto.ConsultationDto;
import com.platform.prism.model.Consultation;
import com.platform.prism.service.ConsultationService;
import com.platform.prism.util.mapper.ConsultationMapper;
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
@RequestMapping("/consultations")
public class ConsultationController {

    private final ConsultationService consultationService;
    private final ConsultationMapper consultationMapper;

    /**
     * Retrieves a specific consultation by its ID.
     *
     * @param id the ID of the consultation to retrieve
     * @return the requested ConsultationDto object, or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<ConsultationDto> getConsultationById(@PathVariable Long id) {
        log.info("Fetching consultation with ID: {}", id);
        try {
            ConsultationDto consultation = consultationService.getConsultationById(id);
            return ResponseEntity.ok(consultation);
        } catch (RuntimeException e) {
            log.warn("Consultation not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves all consultations.
     *
     * @return ResponseEntity containing a list of all consultations as DTOs
     */
    @GetMapping
    public ResponseEntity<List<ConsultationDto>> getAllConsultations() {
        log.info("Fetching all consultations...");
        List<ConsultationDto> consultations = consultationService.getAllConsultations().stream()
                .map(consultationMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(consultations);
    }

    /**
     * Creates a new consultation.
     *
     * @param dto the consultation DTO
     * @return ResponseEntity with the created consultation DTO
     */
    @PostMapping
    public ResponseEntity<ConsultationDto> createConsultation(@Valid @RequestBody ConsultationDto dto) {
        log.info("Creating new consultation of type: {}", dto.getConsultationType());
        ConsultationDto created = consultationService.createConsultation(dto);
        return ResponseEntity.status(201).body(created);
    }

    /**
     * Updates an existing consultation by ID.
     *
     * @param id  the ID of the consultation to update
     * @param dto the updated consultation DTO
     * @return ResponseEntity containing the updated consultation DTO
     */
    @PutMapping("/{id}")
    public ResponseEntity<ConsultationDto> updateConsultation(@PathVariable Long id, @Valid @RequestBody ConsultationDto dto) {
        log.info("Updating consultation with ID: {}", id);
        try {
            ConsultationDto updated = consultationService.updateConsultation(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            log.warn("Failed to update: consultation with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a consultation by ID.
     *
     * @param id the ID of the consultation to delete
     * @return ResponseEntity with deletion status
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConsultation(@PathVariable Long id) {
        log.info("Deleting consultation with ID: {}", id);
        try {
            consultationService.deleteConsultation(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.warn("Failed to delete: consultation with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }
} 