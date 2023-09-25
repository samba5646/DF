package com.geethamsoft.NearByRentalService.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "rental_services")
public class RentalService {

    @Id
    private String id;

    @NotBlank(message = "Service Name is required")
    private String serviceName;

    @NotBlank(message = "Category is required")
    private String category;

    @NotBlank(message = "Location is required")
    private String location;

    @NotBlank(message = "Contact Information is required")
    private String contactInformation;

    @NotBlank(message = "Availability is required")
    private String availability;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Price per Hour is required")
    @DecimalMin(value = "0.0", message = "Price per Hour must be a non-negative number")
    private double pricePerHour;

    @NotNull(message = "Price per Day is required")
    @DecimalMin(value = "0.0", message = "Price per Day must be a non-negative number")
    private double pricePerDay;


    @NotNull(message = "Price per Unit is required")
    @DecimalMin(value = "0.0", message = "Price per Unit must be a non-negative number")
    private double pricePerUnit;

    @NotBlank(message = "Unit of Measurement is required")
    private String unitOfMeasurement;

    @Size(min = 1, message = "At least one feature must be specified")
    private List<@NotBlank(message = "Feature is required") String> features;

}