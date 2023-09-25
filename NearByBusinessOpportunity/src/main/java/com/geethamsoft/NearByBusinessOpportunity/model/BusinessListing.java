package com.geethamsoft.NearByBusinessOpportunity.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "business-opportunity")
public class BusinessListing {
    private String id;

    @NotBlank(message = "Business Name is required")
    private String businessName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Contact Number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Contact Number must be 10 digits")
    private String contactNumber;

    @NotBlank(message = "Location is required")
    private String location;

    @NotBlank(message = "Category is required")
    private String category;

    @NotNull(message = "Minimum Investment is required")
    @DecimalMin(value = "0.0", message = "Minimum Investment must be a non-negative number")
    private double minInvestment;

    @NotNull(message = "Maximum Investment is required")
    @DecimalMin(value = "0.0", message = "Maximum Investment must be a non-negative number")
    private double maxInvestment;

    @NotBlank(message = "Required Skills are required")
    private String requiredSkills;

    @NotBlank(message = "Business Type is required")
    private String businessType;

    @NotBlank(message = "Business Industry is required")
    private String businessIndustry;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating cannot be more than 5")
    private Integer rating;

    @Size(min = 1, message = "At least one language must be spoken")
    private List<@NotBlank(message = "Language is required") String> languagesSpoken;

    @NotBlank(message = "Preferred Contact Method is required")
    private String preferredContactMethod;

    private String websiteUrl;

    private String socialMediaLinks;

    @NotBlank(message = "Business Logo is required")
    private String businessLogoUrl;

    private List<String> businessImagesUrls;

    private List<String> videosUrls;

    private List<String> additionalDocumentsUrls;

    private String paymentMethods;

    private String workingHours;

    private List<String> customerReviews;
}