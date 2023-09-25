package com.geethamsoft.NearByTechniciansAndNonTechnicians.dto;

import lombok.Data;

@Data
public class SearchNearByTechniciansAndNonTechniciansDTO {
    private String keyword;
    private String role;
    private String location;
    private Double minExperience;
    private Double maxExperience;
    private String skills;
    private String languagesSpoken;
    private String availability;
    private String sortBy;

}

