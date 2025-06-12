package com.platform.prism.util.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.platform.prism.dto.*;
import com.platform.prism.enums.ConsultationType;
import com.platform.prism.model.Consultation;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
@Slf4j
public abstract class ConsultationMapper {

    @Autowired
    protected ObjectMapper objectMapper;

    public abstract ConsultationDto toDto(Consultation consultation);

    public abstract Consultation toEntity(ConsultationDto dto);

    public abstract void updateEntityFromDto(ConsultationDto dto, @MappingTarget Consultation consultation);

    @AfterMapping
    protected void handleTypeSpecificData(ConsultationDto dto, @MappingTarget Consultation consultation) {
        if (dto.getTypeSpecificData() == null || dto.getConsultationType() == null) {
            return;
        }

        try {
            switch (dto.getConsultationType()) {
                case ALLERGY_MANAGEMENT -> {
                    AllergyManagementConsultationDto allergyDto = objectMapper.readValue(
                            dto.getTypeSpecificData(),
                            AllergyManagementConsultationDto.class
                    );
                    consultation.setTypeSpecificData(objectMapper.writeValueAsString(allergyDto));
                }
                case NUTRITIONAL -> {
                    NutritionalConsultationDto nutritionalDto = objectMapper.readValue(
                            dto.getTypeSpecificData(),
                            NutritionalConsultationDto.class
                    );
                    consultation.setTypeSpecificData(objectMapper.writeValueAsString(nutritionalDto));
                }
                case DIABETES -> {
                    DiabetesConsultationDto diabetesDto = objectMapper.readValue(
                            dto.getTypeSpecificData(),
                            DiabetesConsultationDto.class
                    );
                    consultation.setTypeSpecificData(objectMapper.writeValueAsString(diabetesDto));
                }
                case BLOOD_PRESSURE -> {
                    BloodPressureConsultationDto bpDto = objectMapper.readValue(
                            dto.getTypeSpecificData(),
                            BloodPressureConsultationDto.class
                    );
                    consultation.setTypeSpecificData(objectMapper.writeValueAsString(bpDto));
                }
                case CARDIAC -> {
                    CardiacConsultationDto cardiacDto = objectMapper.readValue(
                            dto.getTypeSpecificData(),
                            CardiacConsultationDto.class
                    );
                    consultation.setTypeSpecificData(objectMapper.writeValueAsString(cardiacDto));
                }
                case MENTAL_HEALTH -> {
                    MentalHealthConsultationDto mentalHealthDto = objectMapper.readValue(
                            dto.getTypeSpecificData(),
                            MentalHealthConsultationDto.class
                    );
                    consultation.setTypeSpecificData(objectMapper.writeValueAsString(mentalHealthDto));
                }
                case MEDICATION_MANAGEMENT -> {
                    MedicationManagementConsultationDto medicationDto = objectMapper.readValue(
                            dto.getTypeSpecificData(),
                            MedicationManagementConsultationDto.class
                    );
                    consultation.setTypeSpecificData(objectMapper.writeValueAsString(medicationDto));
                }
                case IMMUNIZATION -> {
                    ImmunizationConsultationDto immunizationDto = objectMapper.readValue(
                            dto.getTypeSpecificData(),
                            ImmunizationConsultationDto.class
                    );
                    consultation.setTypeSpecificData(objectMapper.writeValueAsString(immunizationDto));
                }
                case THYROID -> {
                    ThyroidConsultationDto thyroidDto = objectMapper.readValue(
                            dto.getTypeSpecificData(),
                            ThyroidConsultationDto.class
                    );
                    consultation.setTypeSpecificData(objectMapper.writeValueAsString(thyroidDto));
                }
                case WEIGHT_MANAGEMENT -> {
                    WeightManagementConsultationDto weightDto = objectMapper.readValue(
                            dto.getTypeSpecificData(),
                            WeightManagementConsultationDto.class
                    );
                    consultation.setTypeSpecificData(objectMapper.writeValueAsString(weightDto));
                }
                case STRESS_MANAGEMENT -> {
                    StressManagementConsultationDto stressDto = objectMapper.readValue(
                            dto.getTypeSpecificData(),
                            StressManagementConsultationDto.class
                    );
                    consultation.setTypeSpecificData(objectMapper.writeValueAsString(stressDto));
                }
                case SLEEP_HEALTH -> {
                    SleepHealthConsultationDto sleepDto = objectMapper.readValue(
                            dto.getTypeSpecificData(),
                            SleepHealthConsultationDto.class
                    );
                    consultation.setTypeSpecificData(objectMapper.writeValueAsString(sleepDto));
                }
                case PHYSICAL_FITNESS -> {
                    PhysicalFitnessConsultationDto fitnessDto = objectMapper.readValue(
                            dto.getTypeSpecificData(),
                            PhysicalFitnessConsultationDto.class
                    );
                    consultation.setTypeSpecificData(objectMapper.writeValueAsString(fitnessDto));
                }
                case WOMENS_HEALTH -> {
                    WomensHealthConsultationDto womensHealthDto = objectMapper.readValue(
                            dto.getTypeSpecificData(),
                            WomensHealthConsultationDto.class
                    );
                    consultation.setTypeSpecificData(objectMapper.writeValueAsString(womensHealthDto));
                }
                case MENS_HEALTH -> {
                    MensHealthConsultationDto mensHealthDto = objectMapper.readValue(
                            dto.getTypeSpecificData(),
                            MensHealthConsultationDto.class
                    );
                    consultation.setTypeSpecificData(objectMapper.writeValueAsString(mensHealthDto));
                }
            }
        } catch (JsonProcessingException e) {
            log.error("Error processing type-specific data for consultation type: {}", dto.getConsultationType(), e);
            throw new RuntimeException("Error processing type-specific data", e);
        }
    }

    @AfterMapping
    protected void handleTypeSpecificData(Consultation consultation, @MappingTarget ConsultationDto dto) {
        if (consultation.getTypeSpecificData() == null || consultation.getConsultationType() == null) {
            return;
        }

        try {
            switch (consultation.getConsultationType()) {
                case ALLERGY_MANAGEMENT -> {
                    AllergyManagementConsultationDto allergyDto = objectMapper.readValue(
                            consultation.getTypeSpecificData(),
                            AllergyManagementConsultationDto.class
                    );
                    dto.setTypeSpecificData(objectMapper.writeValueAsString(allergyDto));
                }
                case NUTRITIONAL -> {
                    NutritionalConsultationDto nutritionalDto = objectMapper.readValue(
                            consultation.getTypeSpecificData(),
                            NutritionalConsultationDto.class
                    );
                    dto.setTypeSpecificData(objectMapper.writeValueAsString(nutritionalDto));
                }
                case DIABETES -> {
                    DiabetesConsultationDto diabetesDto = objectMapper.readValue(
                            consultation.getTypeSpecificData(),
                            DiabetesConsultationDto.class
                    );
                    dto.setTypeSpecificData(objectMapper.writeValueAsString(diabetesDto));
                }
                case BLOOD_PRESSURE -> {
                    BloodPressureConsultationDto bpDto = objectMapper.readValue(
                            consultation.getTypeSpecificData(),
                            BloodPressureConsultationDto.class
                    );
                    dto.setTypeSpecificData(objectMapper.writeValueAsString(bpDto));
                }
                case CARDIAC -> {
                    CardiacConsultationDto cardiacDto = objectMapper.readValue(
                            consultation.getTypeSpecificData(),
                            CardiacConsultationDto.class
                    );
                    dto.setTypeSpecificData(objectMapper.writeValueAsString(cardiacDto));
                }
                case MENTAL_HEALTH -> {
                    MentalHealthConsultationDto mentalHealthDto = objectMapper.readValue(
                            consultation.getTypeSpecificData(),
                            MentalHealthConsultationDto.class
                    );
                    dto.setTypeSpecificData(objectMapper.writeValueAsString(mentalHealthDto));
                }
                case MEDICATION_MANAGEMENT -> {
                    MedicationManagementConsultationDto medicationDto = objectMapper.readValue(
                            consultation.getTypeSpecificData(),
                            MedicationManagementConsultationDto.class
                    );
                    dto.setTypeSpecificData(objectMapper.writeValueAsString(medicationDto));
                }
                case IMMUNIZATION -> {
                    ImmunizationConsultationDto immunizationDto = objectMapper.readValue(
                            consultation.getTypeSpecificData(),
                            ImmunizationConsultationDto.class
                    );
                    dto.setTypeSpecificData(objectMapper.writeValueAsString(immunizationDto));
                }
                case THYROID -> {
                    ThyroidConsultationDto thyroidDto = objectMapper.readValue(
                            consultation.getTypeSpecificData(),
                            ThyroidConsultationDto.class
                    );
                    dto.setTypeSpecificData(objectMapper.writeValueAsString(thyroidDto));
                }
                case WEIGHT_MANAGEMENT -> {
                    WeightManagementConsultationDto weightDto = objectMapper.readValue(
                            consultation.getTypeSpecificData(),
                            WeightManagementConsultationDto.class
                    );
                    dto.setTypeSpecificData(objectMapper.writeValueAsString(weightDto));
                }
                case STRESS_MANAGEMENT -> {
                    StressManagementConsultationDto stressDto = objectMapper.readValue(
                            consultation.getTypeSpecificData(),
                            StressManagementConsultationDto.class
                    );
                    dto.setTypeSpecificData(objectMapper.writeValueAsString(stressDto));
                }
                case SLEEP_HEALTH -> {
                    SleepHealthConsultationDto sleepDto = objectMapper.readValue(
                            consultation.getTypeSpecificData(),
                            SleepHealthConsultationDto.class
                    );
                    dto.setTypeSpecificData(objectMapper.writeValueAsString(sleepDto));
                }
                case PHYSICAL_FITNESS -> {
                    PhysicalFitnessConsultationDto fitnessDto = objectMapper.readValue(
                            consultation.getTypeSpecificData(),
                            PhysicalFitnessConsultationDto.class
                    );
                    dto.setTypeSpecificData(objectMapper.writeValueAsString(fitnessDto));
                }
                case WOMENS_HEALTH -> {
                    WomensHealthConsultationDto womensHealthDto = objectMapper.readValue(
                            consultation.getTypeSpecificData(),
                            WomensHealthConsultationDto.class
                    );
                    dto.setTypeSpecificData(objectMapper.writeValueAsString(womensHealthDto));
                }
                case MENS_HEALTH -> {
                    MensHealthConsultationDto mensHealthDto = objectMapper.readValue(
                            consultation.getTypeSpecificData(),
                            MensHealthConsultationDto.class
                    );
                    dto.setTypeSpecificData(objectMapper.writeValueAsString(mensHealthDto));
                }
            }
        } catch (JsonProcessingException e) {
            log.error("Error processing type-specific data for consultation type: {}", consultation.getConsultationType(), e);
            throw new RuntimeException("Error processing type-specific data", e);
        }
    }
}
