package com.platform.prism.util.mapper;

import com.platform.prism.dto.MedicationDto;
import com.platform.prism.model.Medication;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)

public interface MedicationMapper {

    MedicationDto toDto(Medication medication);

    Medication toEntity(MedicationDto dto); //this creates a new entity; not used for updates

    void updateEntityFromDto(MedicationDto dto, @MappingTarget Medication medication); //used for updates
}
