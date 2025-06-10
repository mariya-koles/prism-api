package com.platform.prism.service;

import com.platform.prism.dto.AllergyDto;
import com.platform.prism.model.Allergy;
import com.platform.prism.repository.AllergyRepository;
import com.platform.prism.util.mapper.AllergyMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AllergyService {

    private final AllergyRepository allergyRepository;

    private final AllergyMapper allergyMapper;

    public List<Allergy> getAllAllergies() {
        return allergyRepository.findAll();
    }

    public AllergyDto getAllergyById(Long id) {
        return allergyRepository.findById(id)
                .map(allergyMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Allergy not found"));
    }

    public AllergyDto updateAllergy(Long id, AllergyDto dto) {
        Allergy existing = allergyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Allergy not found"));

        allergyMapper.updateEntityFromDto(dto, existing);

        Allergy updated = allergyRepository.save(existing);
        return allergyMapper.toDto(updated);
    }

    public Allergy saveAllergy(Allergy allergy) {
        return allergyRepository.save(allergy);
    }

    public void deleteAllergy(Long id) {
        if (!allergyRepository.existsById(id)) {
            throw new EntityNotFoundException("Allergy not found");
        }
        allergyRepository.deleteById(id);
    }
}
