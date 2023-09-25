package com.geethamsoft.NearByShopsAdsAndOffers.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopAdOfferDTO {
    @NotBlank(message = "Shop name is required")
    private String shopName;

    @NotBlank(message = "Category is required")
    private String category;

    @NotBlank(message = "Location is required")
    private String location;

    @NotBlank(message = "Offer title is required")
    private String offerTitle;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Valid until date is required")
    private Date validUntil;

    @NotNull(message = "Discount percentage is required")
    @DecimalMin(value = "0.0", message = "Discount percentage must be a non-negative number")
    private Double discountPercentage;

    private Double minPurchase;
    private String promoCode;

    @NotBlank(message = "Contact information is required")
    private String contactInformation;


}
