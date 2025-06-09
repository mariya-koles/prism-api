package com.platform.prism.service;

import com.platform.prism.dto.MedicationDto;
import com.platform.prism.model.Medication;
import com.platform.prism.repository.MedicationRepository;
import com.platform.prism.util.mapper.MedicationMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicationService {

    private final MedicationRepository medicationRepository;

    private final MedicationMapper medicationMapper;

    public List<Medication> getAllMedications() {
        return medicationRepository.findAll();
    }

    public MedicationDto getMedicationById(Long id) {
        return medicationRepository.findById(id)
                .map(medicationMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Medication not found"));
    }

    public MedicationDto updateMedication(Long id, MedicationDto dto) {
        Medication existing = medicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Medication not found"));

        medicationMapper.updateEntityFromDto(dto, existing);

        Medication updated = medicationRepository.save(existing);
        return medicationMapper.toDto(updated);
    }

    public Medication saveMedication(Medication medication) {
        return medicationRepository.save(medication);
    }

    public void deleteMedication(Long id) {
        if (!medicationRepository.existsById(id)) {
            throw new EntityNotFoundException("Medication not found");
        }
        medicationRepository.deleteById(id);
    }
}
