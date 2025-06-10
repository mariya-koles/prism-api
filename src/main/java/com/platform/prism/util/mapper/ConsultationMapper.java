package com.platform.prism.util.mapper;

import com.platform.prism.dto.ConsultationDto;
import com.platform.prism.model.Consultation;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ConsultationMapper {

    ConsultationDto toDto(Consultation consultation);

    Consultation toEntity(ConsultationDto dto); // creates new entity

    void updateEntityFromDto(ConsultationDto dto, @MappingTarget Consultation consultation); // used for updates
}
