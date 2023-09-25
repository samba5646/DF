package com.geethamsoft.NearByRentalService.dto;

import lombok.Data;


import java.util.List;

@Data
public class RentalServiceSearchDTO {
    private String category;
    private String location;
    private String availability;
    private double maxPricePerHour;
    private double maxPricePerDay;
    private double maxPricePerUnit;
    private List<String> features;
}
