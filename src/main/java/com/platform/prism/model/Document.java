package com.platform.prism.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Table(name = "documents")
@Schema(description = "Document model representing files attached to a consultation.")
public class Document {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Consultation ID is required")
    @Column(name = "consultation_id", nullable = false)
    private Long consultationId;

    @NotBlank(message = "Filename is required")
    @Column(name = "filename", nullable = false, length = 255)
    private String filename;

    @Lob
    @Column(name = "content", nullable = false)
    private byte[] content;

    @Column(name = "mime_type", length = 100)
    private String mimeType;

    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt;
}
