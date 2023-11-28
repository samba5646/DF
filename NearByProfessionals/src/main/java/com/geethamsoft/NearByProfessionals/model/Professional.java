package com.geethamsoft.NearByProfessionals.model;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "professionals")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Professional {
    @Id
    private String id;

    @NotBlank(message = "Full name is required")
    private String fullName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @Pattern(regexp = "^\\d{10}$", message = "Invalid mobile number format (e.g., 1234567890)")
    private String mobileNumber;

    @NotBlank(message = "Location is required")
    private String location;

    @NotBlank(message = "Type is required")
    private String type;

    @NotBlank(message = "Job title or specialization is required")
    private String jobTitleOrSpecialization;

    @Min(value = 0, message = "Experience must be a positive number")
    private int experience;

    private String skills;

    @NotBlank(message = "Availability is required")
    private String languagesSpoken;

    private String briefBio;

    private String education;

    @NotBlank(message = "Availability is required")
    private String availability;

    @NotBlank(message = "Preferred contact method is required")
    private String preferredContactMethod;

    @DecimalMin(value = "0.0", message = "Minimum price must be a non-negative number")
    private double minPrice;

    @DecimalMax(value = "1000000.0", message = "Maximum price must be less than or equal to 1,000,000")
    private double maxPrice;

}
