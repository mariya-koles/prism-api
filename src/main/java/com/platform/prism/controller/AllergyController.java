package com.platform.prism.controller;

import com.platform.prism.dto.AllergyDto;
import com.platform.prism.model.Allergy;
import com.platform.prism.service.AllergyService;
import com.platform.prism.util.mapper.AllergyMapper;
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
@RequestMapping("/allergies")
public class AllergyController {

    private final AllergyService allergyService;

    private final AllergyMapper allergyMapper;

    /**
     * Retrieves a specific allergy by its ID.
     *
     * @param id the ID of the allergy to retrieve
     * @return the requested AllergyDto object, or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<AllergyDto> getAllergyById(@PathVariable Long id) {
        log.info("Fetching allergy with ID: {}", id);
        try {
            AllergyDto allergy = allergyService.getAllergyById(id);
            return ResponseEntity.ok(allergy);
        } catch (RuntimeException e) {
            log.warn("Allergy not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves all allergies.
     *
     * @return ResponseEntity containing a list of all allergies as DTOs
     */
    @GetMapping
    public ResponseEntity<List<AllergyDto>> getAllAllergies() {
        log.info("Fetching all allergies...");
        List<AllergyDto> allergies = allergyService.getAllAllergies().stream()
                .map(allergyMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(allergies);
    }

    /**
     * Creates a new allergy.
     *
     * @param dto the allergy DTO
     * @return ResponseEntity with the created allergy DTO
     */
    @PostMapping
    public ResponseEntity<AllergyDto> createAllergy(@Valid @RequestBody AllergyDto dto) {
        log.info("Creating new allergy of type: {}", dto.getAllergyType());
        Allergy entity = allergyMapper.toEntity(dto);
        Allergy saved = allergyService.saveAllergy(entity);
        AllergyDto responseDto = allergyMapper.toDto(saved);
        return ResponseEntity.status(201).body(responseDto);
    }

    /**
     * Updates an existing allergy by ID.
     *
     * @param id  the ID of the allergy to update
     * @param dto the updated allergy DTO
     * @return ResponseEntity containing the updated allergy DTO
     */
    @PutMapping("/{id}")
    public ResponseEntity<AllergyDto> updateAllergy(@PathVariable Long id, @Valid @RequestBody AllergyDto dto) {
        log.info("Updating allergy with ID: {}", id);
        try {
            AllergyDto updated = allergyService.updateAllergy(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            log.warn("Failed to update: allergy with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes an allergy by ID.
     *
     * @param id the ID of the allergy to delete
     * @return ResponseEntity with deletion status
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAllergy(@PathVariable Long id) {
        log.info("Deleting allergy with ID: {}", id);
        try {
            allergyService.deleteAllergy(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.warn("Failed to delete: allergy with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }
}
