package com.geethamsoft.NearByBloodDonor.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BloodDonorDTO {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Blood Type is required")
    private String bloodType;

    @NotBlank(message = "Location is required")
    private String location;

    @NotBlank(message = "Availability is required")
    private String availability;

    @NotNull(message = "Last Donation Date is required")
    @PastOrPresent(message = "Last Donation Date cannot be in the future")
    private Date lastDonationDate;

    @NotNull(message = "Age is required")
    @Min(value = 18, message = "Age must be at least 18")
    private Integer age;

    @NotBlank(message = "Gender is required")
    private String gender;

    @NotBlank(message = "Smoking Habit is required")
    private String smokingHabit;

    @NotBlank(message = "Drinking Habit is required")
    private String drinkingHabit;

    @NotBlank(message = "Donation Cost is required")
    private String donationCost;

    private Double donationAmount;

    @NotBlank(message = "Contact Information is required")
    private String contactInformation;
}
