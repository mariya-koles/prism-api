package com.platform.prism.util.mapper;

import com.platform.prism.dto.PatientDto;
import com.platform.prism.model.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PatientMapper {


    PatientDto toDto(Patient patient);

    Patient toEntity(PatientDto dto);

    void updateEntityFromDto(PatientDto dto, @MappingTarget Patient patient); //used for updates
}
