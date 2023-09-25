package com.geethamsoft.NearByBusinessOpportunity.dto;

import lombok.Data;

import java.util.List;

@Data
public class BusinessSearchDTO {
    private String searchKeyword;
    private String category;
    private String location;
    private double minInvestment;
    private double maxInvestment;
    private String requiredSkills;
    private String businessType;
    private String businessIndustry;
    private String sortBy;

}

