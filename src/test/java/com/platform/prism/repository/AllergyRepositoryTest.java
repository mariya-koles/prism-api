package com.platform.prism.repository;

import com.platform.prism.model.Allergy;
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
class AllergyRepositoryTest {

    @Autowired
    private AllergyRepository allergyRepository;

    @Test
    @DisplayName("shouldSaveAndRetrieveAllergyById")
    void shouldSaveAndRetrieveAllergyById() {
        Allergy allergy = Allergy.builder()
                .allergyType("Drug")
                .drugName("Penicillin")
                .allergyName(null)
                .build();

        Allergy saved = allergyRepository.save(allergy);

        Optional<Allergy> retrieved = allergyRepository.findById(saved.getId());

        assertThat(retrieved).isPresent();
        assertThat(retrieved.get().getDrugName()).isEqualTo("Penicillin");
        assertThat(retrieved.get().getAllergyType()).isEqualTo("Drug");
    }

    @Test
    @DisplayName("shouldReturnAllAllergies")
    void shouldReturnAllAllergies() {
        Allergy allergy1 = Allergy.builder()
                .allergyType("Drug")
                .drugName("Aspirin")
                .allergyName(null)
                .build();

        Allergy allergy2 = Allergy.builder()
                .allergyType("Non-Drug")
                .allergyName("Shellfish")
                .drugName(null)
                .build();

        allergyRepository.saveAll(List.of(allergy1, allergy2));

        List<Allergy> allergies = allergyRepository.findAll();

        assertThat(allergies).hasSize(2);
        assertThat(allergies).extracting("allergyType").contains("Drug", "Non-Drug");
    }

    @Test
    @DisplayName("shouldDeleteAllergy")
    void shouldDeleteAllergy() {
        Allergy allergy = Allergy.builder()
                .allergyType("Non-Drug")
                .allergyName("DeleteMe")
                .drugName(null)
                .build();

        Allergy saved = allergyRepository.save(allergy);
        Long id = saved.getId();

        allergyRepository.deleteById(id);

        Optional<Allergy> result = allergyRepository.findById(id);

        assertThat(result).isNotPresent();
    }

    @Test
    @DisplayName("shouldSaveNonDrugAllergy")
    void shouldSaveNonDrugAllergy() {
        Allergy nonDrugAllergy = Allergy.builder()
                .allergyType("Non-Drug")
                .allergyName("Peanuts")
                .drugName(null)
                .build();

        Allergy saved = allergyRepository.save(nonDrugAllergy);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getAllergyName()).isEqualTo("Peanuts");
        assertThat(saved.getDrugName()).isNull();
    }
} 