package com.platform.prism.util.mapper;

import com.platform.prism.dto.PatientDto;
import com.platform.prism.model.Patient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    PatientDto toDto(Patient patient);

    Patient toEntity(PatientDto dto);
}
