package com.geethamsoft.NearByTechniciansAndNonTechnicians.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "NearByTechniciansAndNonTechnicians")
public class NearByTechniciansAndNonTechnicians {

    @Id
    private String id;

    @NotBlank(message = "Full Name is required")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Mobile Number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Mobile Number must be 10 digits")
    private String mobileNumber;

    @NotBlank(message = "Location is required")
    private String location;

    @NotBlank(message = "Role is required")
    private String role;

    @NotBlank(message = "Job Title or Specialization is required")
    private String jobTitle;

    @NotBlank(message = "Experience is required")
    @Pattern(regexp = "^(\\d+\\.?\\d*)?$", message = "Invalid experience format")
    private String experience;

    @NotBlank(message = "Skills are required")
    private String skills;

    @NotBlank(message = "Brief Bio is required")
    private String briefBio;

    private String education;

    @Size(min = 1, message = "At least one language must be spoken")
    private List<@NotBlank(message = "Language is required") String> languagesSpoken;

    @NotBlank(message = "Preferred Contact Method is required")
    private String preferredContactMethod;

    private String availability;

    private Double rating;

    // Constructors, getters, and setters
}
