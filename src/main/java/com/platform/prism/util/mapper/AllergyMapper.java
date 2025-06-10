package com.platform.prism.util.mapper;

import com.platform.prism.dto.AllergyDto;
import com.platform.prism.model.Allergy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AllergyMapper {

    AllergyDto toDto(Allergy allergy);

    Allergy toEntity(AllergyDto dto); //this creates a new entity; not used for updates

    void updateEntityFromDto(AllergyDto dto, @MappingTarget Allergy allergy); //used for updates
}
