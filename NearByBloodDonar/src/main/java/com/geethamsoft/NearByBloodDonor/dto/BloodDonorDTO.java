package com.geethamsoft.NearByBloodDonor.dto;

import com.geethamsoft.NearByBloodDonor.validations.Age;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
    @Age(minimum = 18L, maximum = 50L, message = "Age should be between 18 and 50")
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
    private Double costRangeMin;

    private Double costRangeMax;

    @NotNull(message = "Minimum Age is required")
    @Min(value = 18, message = "Minimum Age must be at least 18")
    private Integer minAge;

    @NotNull(message = "Maximum Age is required")
    @Min(value = 18, message = "Maximum Age must be at least 18")
    private Integer maxAge;

    private List<String> languagesSpoken;

    private String preferredContactMethod;

    private String websiteUrl;

    private String socialMediaLinks;

    private String bloodDonationCenterName;

    private String additionalNotes;

}
