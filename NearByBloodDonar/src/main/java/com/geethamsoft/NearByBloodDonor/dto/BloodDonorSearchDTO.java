package com.geethamsoft.NearByBloodDonor.dto;


import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BloodDonorSearchDTO {

    private String bloodType;
    private String location;
    private String availability;
    private Date lastDonationDate;
    private Integer minAge;
    private Integer maxAge;
    private String gender;
    private String smokingHabit;
    private String drinkingHabit;
    private String donationCost;
    private Double costRangeMin;
    private Double costRangeMax;
    private List<String> languagesSpoken;
    private String preferredContactMethod;

}

