package com.platform.prism.service;

import com.platform.prism.dto.DocumentDto;
import com.platform.prism.model.Document;
import com.platform.prism.repository.DocumentRepository;
import com.platform.prism.util.mapper.DocumentMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;

    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public DocumentDto getDocumentById(Long id) {
        return documentRepository.findById(id)
                .map(documentMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Document not found"));
    }

    @Transactional
    public DocumentDto updateDocument(Long id, DocumentDto dto) {
        Document existing = documentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Document not found"));

        documentMapper.updateEntityFromDto(dto, existing);

        Document updated = documentRepository.save(existing);
        return documentMapper.toDto(updated);
    }

    @Transactional
    public Document saveDocument(Document document) {
        return documentRepository.save(document);
    }

    @Transactional
    public void deleteDocument(Long id) {
        if (!documentRepository.existsById(id)) {
            throw new EntityNotFoundException("Document not found");
        }
        documentRepository.deleteById(id);
    }

    @Transactional
    public DocumentDto createDocument(DocumentDto dto) {
        Document entity = documentMapper.toEntity(dto);
        Document saved = documentRepository.save(entity);
        return documentMapper.toDto(saved);
    }
} 