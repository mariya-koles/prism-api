package com.platform.prism.service;

import com.platform.prism.dto.DocumentDto;
import com.platform.prism.model.Document;
import com.platform.prism.repository.DocumentRepository;
import com.platform.prism.util.mapper.DocumentMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class DocumentServiceTest {

    private DocumentRepository documentRepository;
    private DocumentMapper documentMapper;
    private DocumentService documentService;

    @BeforeEach
    void setUp() {
        documentRepository = mock(DocumentRepository.class);
        documentMapper = Mappers.getMapper(DocumentMapper.class); // use real mapper
        documentService = new DocumentService(documentRepository, documentMapper);
    }

    @Test
    void shouldReturnAllDocuments() {
        Document document1 = Document.builder()
                .filename("test1.pdf")
                .content("test content 1".getBytes())
                .mimeType("application/pdf")
                .uploadedAt(LocalDateTime.now())
                .build();
        Document document2 = Document.builder()
                .filename("test2.pdf")
                .content("test content 2".getBytes())
                .mimeType("application/pdf")
                .uploadedAt(LocalDateTime.now())
                .build();
        when(documentRepository.findAll()).thenReturn(Arrays.asList(document1, document2));

        List<Document> result = documentService.getAllDocuments();

        assertThat(result).hasSize(2).extracting("filename").contains("test1.pdf", "test2.pdf");
    }

    @Test
    void shouldReturnDocumentDto_whenIdExists() {
        Document document = Document.builder()
                .id(1L)
                .filename("test.pdf")
                .content("test content".getBytes())
                .mimeType("application/pdf")
                .uploadedAt(LocalDateTime.now())
                .build();
        when(documentRepository.findById(1L)).thenReturn(Optional.of(document));

        DocumentDto dto = documentService.getDocumentById(1L);

        assertThat(dto).isNotNull();
        assertThat(dto.getFilename()).isEqualTo("test.pdf");
        assertThat(dto.getMimeType()).isEqualTo("application/pdf");
    }

    @Test
    void shouldThrowException_whenDocumentNotFoundById() {
        when(documentRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> documentService.getDocumentById(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Document not found");
    }

    @Test
    void shouldUpdateDocument_whenIdExists() {
        Document existing = Document.builder()
                .id(1L)
                .filename("old.pdf")
                .content("old content".getBytes())
                .mimeType("application/pdf")
                .uploadedAt(LocalDateTime.now())
                .build();

        DocumentDto incoming = DocumentDto.builder()
                .filename("new.pdf")
                .content("new content".getBytes())
                .mimeType("application/pdf")
                .uploadedAt(LocalDateTime.now())
                .build();

        when(documentRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(documentRepository.save(any(Document.class))).thenAnswer(i -> i.getArgument(0));

        DocumentDto updated = documentService.updateDocument(1L, incoming);

        assertThat(updated.getFilename()).isEqualTo("new.pdf");
        assertThat(updated.getMimeType()).isEqualTo("application/pdf");
    }

    @Test
    void shouldSaveDocument() {
        Document newDocument = Document.builder()
                .filename("test.pdf")
                .content("test content".getBytes())
                .mimeType("application/pdf")
                .uploadedAt(LocalDateTime.now())
                .build();

        when(documentRepository.save(newDocument)).thenReturn(newDocument);

        Document saved = documentService.saveDocument(newDocument);

        assertThat(saved).isNotNull();
        assertThat(saved.getFilename()).isEqualTo("test.pdf");
    }

    @Test
    void shouldDeleteDocument_whenIdExists() {
        when(documentRepository.existsById(1L)).thenReturn(true);

        documentService.deleteDocument(1L);

        verify(documentRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldThrowException_whenDeletingNonExistentDocument() {
        when(documentRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> documentService.deleteDocument(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Document not found");
    }

    @Test
    void shouldCreateDocument() {
        DocumentDto dto = DocumentDto.builder()
                .filename("test.pdf")
                .content("test content".getBytes())
                .mimeType("application/pdf")
                .uploadedAt(LocalDateTime.now())
                .build();

        Document entity = Document.builder()
                .filename("test.pdf")
                .content("test content".getBytes())
                .mimeType("application/pdf")
                .uploadedAt(LocalDateTime.now())
                .build();

        when(documentRepository.save(any(Document.class))).thenReturn(entity);

        DocumentDto created = documentService.createDocument(dto);

        assertThat(created).isNotNull();
        assertThat(created.getFilename()).isEqualTo("test.pdf");
        assertThat(created.getMimeType()).isEqualTo("application/pdf");
    }
} 