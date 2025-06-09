package com.platform.prism.repository;

import com.platform.prism.model.Medication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class MedicationRepositoryTest {

    @Autowired
    private MedicationRepository medicationRepository;

    @Test
    @DisplayName("shouldSaveAndRetrieveMedicationById")
    void shouldSaveAndRetrieveMedicationById() {
        Medication med = Medication.builder()
                .proprietaryName("Tylenol")
                .ndcPackageCode("50580-139-04")
                .strength("160mg/5mL")
                .dosageForm("SUSPENSION")
                .route("ORAL")
                .build();

        Medication saved = medicationRepository.save(med);

        Optional<Medication> retrieved = medicationRepository.findById(saved.getId());

        assertThat(retrieved).isPresent();
        assertThat(retrieved.get().getProprietaryName()).isEqualTo("Tylenol");
    }

    @Test
    @DisplayName("shouldReturnAllMedications")
    void shouldReturnAllMedications() {
        Medication med1 = Medication.builder()
                .proprietaryName("Aspirin")
                .ndcPackageCode("11111-0001")
                .build();

        Medication med2 = Medication.builder()
                .proprietaryName("Ibuprofen")
                .ndcPackageCode("22222-0002")
                .build();

        medicationRepository.saveAll(List.of(med1, med2));

        List<Medication> medications = medicationRepository.findAll();

        assertThat(medications).hasSize(2);
        assertThat(medications).extracting("proprietaryName").contains("Aspirin", "Ibuprofen");
    }

    @Test
    @DisplayName("shouldDeleteMedication")
    void shouldDeleteMedication() {
        Medication med = Medication.builder()
                .proprietaryName("DeleteMe")
                .ndcPackageCode("99999-0000")
                .build();

        Medication saved = medicationRepository.save(med);
        Long id = saved.getId();

        medicationRepository.deleteById(id);

        Optional<Medication> result = medicationRepository.findById(id);

        assertThat(result).isNotPresent();
    }
}
