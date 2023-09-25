package com.geethamsoft.NearByBuyAndSellProducts.model;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;


@Document(collection = "NearByBuyAndSellProduct")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    private String id;

    @NotBlank(message = "Product name is required")
    private String name;

    @NotBlank(message = "Category is required")
    private String category;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", message = "Price must be a non-negative number")
    private double price;

    @NotBlank(message = "Condition is required")
    private String condition;

    @NotBlank(message = "Availability is required")
    private String availability;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Contact information is required")
    private String contactInfo;

    private transient MultipartFile productImage; // For storing product image

    // Getters and setters for all fields, including productImage

    public MultipartFile getProductImage() {
        return productImage;
    }

    public void setProductImage(MultipartFile productImage) {
        this.productImage = productImage;
    }
}
