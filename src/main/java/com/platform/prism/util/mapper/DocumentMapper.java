package com.platform.prism.util.mapper;

import com.platform.prism.dto.DocumentDto;
import com.platform.prism.model.Document;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DocumentMapper {

    DocumentDto toDto(Document document);

    Document toEntity(DocumentDto dto);

    void updateEntityFromDto(DocumentDto dto, @MappingTarget Document document);
}
