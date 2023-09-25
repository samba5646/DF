package com.geethamsoft.NearByShopsAdsAndOffers.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


import java.util.Date;

@Data
public class ShopAdOfferSearchDTO {
    private String keyword;
    private String category;
    private String location;
    private String sortBy;
    private String availability;

    @DecimalMin(value = "0.0", message = "Minimum discount must be a non-negative number")
    private Double minDiscount;

    @DecimalMin(value = "0.0", message = "Maximum discount must be a non-negative number")
    private Double maxDiscount;

    @NotNull(message = "Minimum purchase must be specified")
    private Double minPurchase;

    @NotBlank(message = "Shop name is required")
    private String shopName;

    @NotNull(message = "Valid until date is required")
    private Date validUntil;


}

