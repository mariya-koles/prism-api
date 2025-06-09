package com.platform.prism.util.mapper;

import com.platform.prism.dto.MedicationDto;
import com.platform.prism.model.Medication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class MedicationMapperTest {

    private MedicationMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(MedicationMapper.class);
    }

    @Test
    void shouldMapDtoToMedication() {
        MedicationDto dto = MedicationDto.builder()
                .id(1L)
                .proprietaryName("Tylenol")
                .ndcPackageCode("50580-139-04")
                .strength("160mg/5mL")
                .dosageForm("SUSPENSION")
                .route("ORAL")
                .monographId("M013")
                .labelerName("Kenvue Brands LLC")
                .productNdc("50580-139")
                .nonProprietaryName("Acetaminophen")
                .substanceName("ACETAMINOPHEN")
                .productTypeName("HUMAN OTC DRUG")
                .build();

        Medication entity = mapper.toEntity(dto);

        assertThat(entity.getProprietaryName()).isEqualTo("Tylenol");
        assertThat(entity.getNdcPackageCode()).isEqualTo("50580-139-04");
        assertThat(entity.getSubstanceName()).isEqualTo("ACETAMINOPHEN");
    }

    @Test
    void shouldMapMedicationToDto() {
        Medication entity = Medication.builder()
                .id(2L)
                .proprietaryName("Aspirin")
                .ndcPackageCode("11111-2222")
                .strength("81mg")
                .productTypeName("HUMAN OTC DRUG")
                .build();

        MedicationDto dto = mapper.toDto(entity);

        assertThat(dto.getId()).isEqualTo(2L);
        assertThat(dto.getProprietaryName()).isEqualTo("Aspirin");
        assertThat(dto.getNdcPackageCode()).isEqualTo("11111-2222");
    }

    @Test
    void shouldUpdateMedicationFromDto() {
        Medication existing = Medication.builder()
                .id(3L)
                .proprietaryName("Old Name")
                .ndcPackageCode("00000-0000")
                .build();

        MedicationDto dto = MedicationDto.builder()
                .proprietaryName("Updated Name")
                .ndcPackageCode("99999-9999")
                .build();

        mapper.updateEntityFromDto(dto, existing);

        assertThat(existing.getProprietaryName()).isEqualTo("Updated Name");
        assertThat(existing.getNdcPackageCode()).isEqualTo("99999-9999");
        assertThat(existing.getId()).isEqualTo(3L); // unchanged
    }

    @Test
    void shouldIgnoreNulls_whenUpdatingFromDto() {
        Medication existing = Medication.builder()
                .proprietaryName("Original")
                .ndcPackageCode("12345-6789")
                .route("ORAL")
                .build();

        MedicationDto dto = MedicationDto.builder()
                .proprietaryName(null) // null field
                .ndcPackageCode("UPDATED-0001")
                .build();

        mapper.updateEntityFromDto(dto, existing);

        assertThat(existing.getProprietaryName()).isEqualTo("Original"); // unchanged
        assertThat(existing.getNdcPackageCode()).isEqualTo("UPDATED-0001"); // updated
    }
}
