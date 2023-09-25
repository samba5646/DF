package com.geethamsoft.NearByCabsAndVehicleBooking.dto;

import lombok.Data;

@Data
public class VehicleSearchDTO {
    private String location;
    private String vehicleType;
    private String transmission;
    private String fuelType;
    private Integer minSeats;
    private Double maxPrice;
    private String availability;
    private String keyword;
    private String vehicleName;
    private String modelYear;
    private String licensePlate;
    private String contactInformation;
    // Add more search criteria fields as needed
}

