package com.platform.prism.controller;

import com.platform.prism.dto.DocumentDto;
import com.platform.prism.model.Document;
import com.platform.prism.service.DocumentService;
import com.platform.prism.util.mapper.DocumentMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@SecurityRequirement(name = "basicAuth")
@RequiredArgsConstructor
@RequestMapping("/documents")
public class DocumentController {

    private final DocumentService documentService;
    private final DocumentMapper documentMapper;

    /**
     * Retrieves a specific document by its ID.
     *
     * @param id the ID of the document to retrieve
     * @return the requested DocumentDto object, or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<DocumentDto> getDocumentById(@PathVariable Long id) {
        log.info("Fetching document with ID: {}", id);
        try {
            DocumentDto document = documentService.getDocumentById(id);
            return ResponseEntity.ok(document);
        } catch (RuntimeException e) {
            log.warn("Document not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves all documents.
     *
     * @return ResponseEntity containing a list of all documents as DTOs
     */
    @GetMapping
    public ResponseEntity<List<DocumentDto>> getAllDocuments() {
        log.info("Fetching all documents...");
        List<DocumentDto> documents = documentService.getAllDocuments().stream()
                .map(documentMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(documents);
    }

    /**
     * Creates a new document.
     *
     * @param dto the document DTO
     * @return ResponseEntity with the created document DTO
     */
    @PostMapping
    public ResponseEntity<DocumentDto> createDocument(@Valid @RequestBody DocumentDto dto) {
        log.info("Creating new document: {}", dto.getFilename());
        DocumentDto created = documentService.createDocument(dto);
        return ResponseEntity.status(201).body(created);
    }

    /**
     * Updates an existing document by ID.
     *
     * @param id  the ID of the document to update
     * @param dto the updated document DTO
     * @return ResponseEntity containing the updated document DTO
     */
    @PutMapping("/{id}")
    public ResponseEntity<DocumentDto> updateDocument(@PathVariable Long id, @Valid @RequestBody DocumentDto dto) {
        log.info("Updating document with ID: {}", id);
        try {
            DocumentDto updated = documentService.updateDocument(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            log.warn("Failed to update: document with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a document by ID.
     *
     * @param id the ID of the document to delete
     * @return ResponseEntity with deletion status
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        log.info("Deleting document with ID: {}", id);
        try {
            documentService.deleteDocument(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.warn("Failed to delete: document with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }
} 