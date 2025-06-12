package com.platform.prism.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.platform.prism.dto.ConsultationDto;
import com.platform.prism.model.Consultation;
import com.platform.prism.repository.ConsultationRepository;
import com.platform.prism.util.mapper.ConsultationMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultationService {

    private final ConsultationRepository consultationRepository;
    private final ConsultationMapper consultationMapper;
    private final ObjectMapper objectMapper;

    public List<Consultation> getAllConsultations() {
        return consultationRepository.findAll();
    }

    @Transactional(readOnly = true)
    public ConsultationDto getConsultationById(Long id) {
        return consultationRepository.findById(id)
                .map(consultationMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Consultation not found"));
    }

    @Transactional
    public ConsultationDto updateConsultation(Long id, ConsultationDto dto) {
        Consultation existing = consultationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Consultation not found"));

        consultationMapper.updateEntityFromDto(dto, existing);

        Consultation updated = consultationRepository.save(existing);
        return consultationMapper.toDto(updated);
    }

    @Transactional
    public Consultation saveConsultation(Consultation consultation) {
        return consultationRepository.save(consultation);
    }

    @Transactional
    public void deleteConsultation(Long id) {
        if (!consultationRepository.existsById(id)) {
            throw new EntityNotFoundException("Consultation not found");
        }
        consultationRepository.deleteById(id);
    }

    @Transactional
    public ConsultationDto createConsultation(ConsultationDto dto) {
        Consultation entity = consultationMapper.toEntity(dto);
        Consultation saved = consultationRepository.save(entity);
        return consultationMapper.toDto(saved);
    }
} 