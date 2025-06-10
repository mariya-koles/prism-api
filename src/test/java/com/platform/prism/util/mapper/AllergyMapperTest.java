package com.platform.prism.util.mapper;

import com.platform.prism.dto.AllergyDto;
import com.platform.prism.model.Allergy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class AllergyMapperTest {

    private AllergyMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(AllergyMapper.class);
    }

    @Test
    void shouldMapDtoToAllergy() {
        AllergyDto dto = AllergyDto.builder()
                .id(1L)
                .allergyType("Drug")
                .drugName("Penicillin")
                .allergyName(null)
                .build();

        Allergy entity = mapper.toEntity(dto);

        assertThat(entity.getId()).isEqualTo(1L);
        assertThat(entity.getAllergyType()).isEqualTo("Drug");
        assertThat(entity.getDrugName()).isEqualTo("Penicillin");
        assertThat(entity.getAllergyName()).isNull();
    }

    @Test
    void shouldMapAllergyToDto() {
        Allergy allergy = Allergy.builder()
                .id(2L)
                .allergyType("Non-Drug")
                .allergyName("Shellfish")
                .drugName(null)
                .build();

        AllergyDto dto = mapper.toDto(allergy);

        assertThat(dto.getId()).isEqualTo(2L);
        assertThat(dto.getAllergyType()).isEqualTo("Non-Drug");
        assertThat(dto.getAllergyName()).isEqualTo("Shellfish");
        assertThat(dto.getDrugName()).isNull();
    }

    @Test
    void shouldUpdateAllergyFromDto() {
        Allergy existing = Allergy.builder()
                .id(3L)
                .allergyType("Drug")
                .drugName("OldDrug")
                .allergyName(null)
                .build();

        AllergyDto dto = AllergyDto.builder()
                .allergyType("Drug")
                .drugName("UpdatedDrug")
                .build();

        mapper.updateEntityFromDto(dto, existing);

        assertThat(existing.getAllergyType()).isEqualTo("Drug");
        assertThat(existing.getDrugName()).isEqualTo("UpdatedDrug");
        assertThat(existing.getId()).isEqualTo(3L); // unchanged
    }

    @Test
    void shouldIgnoreNulls_whenUpdatingFromDto() {
        Allergy existing = Allergy.builder()
                .allergyType("Non-Drug")
                .allergyName("Preserved")
                .drugName(null)
                .build();

        AllergyDto dto = AllergyDto.builder()
                .allergyType(null)
                .allergyName("Updated")
                .build();

        mapper.updateEntityFromDto(dto, existing);

        assertThat(existing.getAllergyType()).isEqualTo("Non-Drug"); // unchanged due to null
        assertThat(existing.getAllergyName()).isEqualTo("Updated"); // updated
    }
} 