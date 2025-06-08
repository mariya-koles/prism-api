package com.platform.prism.util.mapper;

import com.platform.prism.dto.PatientDto;
import com.platform.prism.model.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    PatientMapper INSTANCE = Mappers.getMapper(PatientMapper.class);

    PatientDto toDto(Patient patient);

    Patient toEntity(PatientDto dto);
}
