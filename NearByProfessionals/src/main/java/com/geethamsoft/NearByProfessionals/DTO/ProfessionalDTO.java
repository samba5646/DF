package com.geethamsoft.NearByProfessionals.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalDTO {
    private String fullName;
    private String email;
    private String mobileNumber;
    private String location;
    private String type; // Photographer, Architect, Lawyer, Chef, etc.
    private String jobTitleOrSpecialization;
    private int experience; // Years of experience
    private String skills;
    private String languagesSpoken;
    private String briefBio;
    private String education;
    private String availability; // Full-Time, Part-Time, Contract, etc.
    private String preferredContactMethod; // Email, Phone, etc.
    private double minPrice; // Minimum price
    private double maxPrice; // Maximum price
}
