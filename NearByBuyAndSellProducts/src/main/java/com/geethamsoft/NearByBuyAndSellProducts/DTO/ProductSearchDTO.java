package com.geethamsoft.NearByBuyAndSellProducts.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSearchDTO {
    private String keyword;
    private String category;
    private String location;
    private Double minPrice;
    private Double maxPrice;
    private String condition;
    private String availability;
    private String sortBy;
}
