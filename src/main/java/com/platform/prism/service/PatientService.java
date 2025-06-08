package com.platform.prism.service;

import com.platform.prism.dto.PatientDto;
import com.platform.prism.model.Patient;
import com.platform.prism.repository.PatientRepository;
import com.platform.prism.util.mapper.PatientMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public PatientDto getPatientById(Long id) {
        return patientRepository.findById(id)
                .map(patientMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));
    }

    public PatientDto updatePatient(Long id, PatientDto dto) {
        Patient existing = patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));

        existing.setFirstName(dto.getFirstName());
        existing.setLastName(dto.getLastName());
        existing.setDateOfBirth(dto.getDateOfBirth());

        Patient updated = patientRepository.save(existing);
        return patientMapper.toDto(updated);
    }

    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new EntityNotFoundException("Patient not found");
        }
        patientRepository.deleteById(id);
    }


}
