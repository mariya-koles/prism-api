package com.platform.prism.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Table(name = "allergy")
@Schema(description = "Allergy model representing patient drug or non-drug allergens.")
public class Allergy {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "allergy_type", nullable = false, length = 50)
    @NotBlank(message = "Allergy type is required (Drug or Non-Drug)")
    private String allergyType;

    @Column(name = "allergy_name", length = 255)
    private String allergyName;

    @Column(name = "drug_name", length = 255)
    private String drugName;

}