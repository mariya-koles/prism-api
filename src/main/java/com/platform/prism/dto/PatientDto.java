package com.platform.prism.dto;

import com.platform.prism.model.Patient;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for {@link Patient}
 */
@Schema(description = "Data Transfer Object for Patients")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PatientDto implements Serializable {

    @Schema(description = "Patient ID", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    Long id;

    @NotBlank(message = "First name is required")
    @Schema(description = "First name of the patient", example = "John")
    String firstName;

    @NotBlank(message = "Last name is required")
    @Schema(description = "Last name of the patient", example = "Doe")
    String lastName;

    @Min(message = "Age must be non-negative", value = 0)
    @Schema(description = "Age of the patient", example = "45")
    Integer age;

    @NotNull(message = "Date of birth is required")
    @Schema(description = "Date of birth", example = "1980-05-15")
    LocalDate dateOfBirth;

    @Schema(description = "Home phone number", example = "555-123-4567")
    String phoneNumber;

    @Schema(description = "Mobile phone number", example = "555-987-6543")
    String mobileNumber;

    @Email(message = "Email should be valid")
    @Schema(description = "Email address", example = "john.doe@example.com")
    String email;

    @Schema(description = "Street address", example = "123 Main St")
    String streetAddress;

    @Schema(description = "City", example = "Houston")
    String city;

    @Schema(description = "State", example = "Texas")
    String state;

    @Schema(description = "Zipcode", example = "77001")
    String zipcode;

    @Schema(description = "Additional notes about the patient", example = "Allergic to penicillin")
    String notes;

    @Schema(description = "Timestamp when the patient record was created", example = "2024-06-01T12:34:56")
    LocalDateTime createdAt;

    @Schema(description = "Timestamp when the patient record was last updated", example = "2024-06-01T13:45:00")
    LocalDateTime updatedAt;
}
