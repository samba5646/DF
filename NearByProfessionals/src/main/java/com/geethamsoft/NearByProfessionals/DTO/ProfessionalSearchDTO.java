package com.geethamsoft.NearByProfessionals.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalSearchDTO {
    private String keyword;
    private String type; // Photographer, Architect, Lawyer, Chef, etc.
    private String location;
    private int minExperience; // Minimum years of experience
    private int maxExperience; // Maximum years of experience
    private String skills;
    private String languagesSpoken;
    private String availability; // Full-Time, Part-Time, Contract, etc.
    private String preferredContactMethod; // Email, Phone, etc.
    private double minPrice; // Minimum price
    private double maxPrice; // Maximum price

}