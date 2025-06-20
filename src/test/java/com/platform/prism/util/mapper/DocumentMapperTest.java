package com.platform.prism.util.mapper;

import com.platform.prism.dto.DocumentDto;
import com.platform.prism.model.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class DocumentMapperTest {

    @Autowired
    private DocumentMapper documentMapper;

    private Document document;
    private DocumentDto dto;

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.now();
        
        document = Document.builder()
                .id(1L)
                .consultationId(1L)
                .filename("test.pdf")
                .content("test content".getBytes())
                .mimeType("application/pdf")
                .uploadedAt(now)
                .build();

        dto = DocumentDto.builder()
                .id(1L)
                .consultationId(1L)
                .filename("test.pdf")
                .content("test content".getBytes())
                .mimeType("application/pdf")
                .uploadedAt(now)
                .build();
    }

    @Test
    void shouldMapEntityToDto() {
        DocumentDto result = documentMapper.toDto(document);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(document.getId());
        assertThat(result.getConsultationId()).isEqualTo(document.getConsultationId());
        assertThat(result.getFilename()).isEqualTo(document.getFilename());
        assertThat(result.getMimeType()).isEqualTo(document.getMimeType());
    }

    @Test
    void shouldMapDtoToEntity() {
        Document result = documentMapper.toEntity(dto);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(dto.getId());
        assertThat(result.getConsultationId()).isEqualTo(dto.getConsultationId());
        assertThat(result.getFilename()).isEqualTo(dto.getFilename());
        assertThat(result.getMimeType()).isEqualTo(dto.getMimeType());
    }

    @Test
    void shouldUpdateEntityFromDto() {
        Document existing = Document.builder().build();
        
        documentMapper.updateEntityFromDto(dto, existing);

        assertThat(existing.getConsultationId()).isEqualTo(dto.getConsultationId());
        assertThat(existing.getFilename()).isEqualTo(dto.getFilename());
        assertThat(existing.getMimeType()).isEqualTo(dto.getMimeType());
    }
} 