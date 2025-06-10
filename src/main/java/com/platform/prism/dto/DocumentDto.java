package com.platform.prism.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.platform.prism.model.Document}
 */
@Schema(description = "Data Transfer Object for Documents")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DocumentDto implements Serializable {

    @Schema(description = "Document ID", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotNull(message = "Consultation ID is required")
    @Schema(description = "ID of the consultation this document is attached to", example = "101")
    private Long consultationId;

    @NotBlank(message = "Filename is required")
    @Schema(description = "Name of the uploaded file", example = "lab-results.pdf")
    private String filename;

    @Schema(description = "MIME type of the document", example = "application/pdf")
    private String mimeType;

    @Schema(description = "Binary content of the document", accessMode = Schema.AccessMode.WRITE_ONLY)
    private byte[] content;

    @Schema(description = "Time when the document was uploaded", example = "2025-06-10T10:00:00")
    private LocalDateTime uploadedAt;
}
