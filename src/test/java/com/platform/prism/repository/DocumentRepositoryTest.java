package com.platform.prism.repository;

import com.platform.prism.model.Document;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class DocumentRepositoryTest {

    @Autowired
    private DocumentRepository documentRepository;

    @Test
    @DisplayName("shouldSaveAndRetrieveDocumentById")
    void shouldSaveAndRetrieveDocumentById() {
        Document document = Document.builder()
                .consultationId(1L)
                .filename("test.pdf")
                .content("test content".getBytes())
                .mimeType("application/pdf")
                .uploadedAt(LocalDateTime.now())
                .build();

        Document saved = documentRepository.save(document);

        Optional<Document> retrieved = documentRepository.findById(saved.getId());

        assertThat(retrieved).isPresent();
        assertThat(retrieved.get().getConsultationId()).isEqualTo(1L);
        assertThat(retrieved.get().getFilename()).isEqualTo("test.pdf");
        assertThat(retrieved.get().getMimeType()).isEqualTo("application/pdf");
    }

    @Test
    @DisplayName("shouldReturnAllDocuments")
    void shouldReturnAllDocuments() {
        Document document1 = Document.builder()
                .consultationId(1L)
                .filename("test1.pdf")
                .content("test content 1".getBytes())
                .mimeType("application/pdf")
                .build();

        Document document2 = Document.builder()
                .consultationId(2L)
                .filename("test2.pdf")
                .content("test content 2".getBytes())
                .mimeType("application/pdf")
                .build();

        documentRepository.saveAll(List.of(document1, document2));

        List<Document> documents = documentRepository.findAll();

        assertThat(documents).hasSize(2);
        assertThat(documents).extracting("filename")
                .contains("test1.pdf", "test2.pdf");
    }

    @Test
    @DisplayName("shouldDeleteDocument")
    void shouldDeleteDocument() {
        Document document = Document.builder()
                .consultationId(1L)
                .filename("test.pdf")
                .content("test content".getBytes())
                .mimeType("application/pdf")
                .build();

        Document saved = documentRepository.save(document);
        Long id = saved.getId();

        documentRepository.deleteById(id);

        Optional<Document> result = documentRepository.findById(id);

        assertThat(result).isNotPresent();
    }
} 