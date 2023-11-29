package com.geethamsoft.NearbyBusinessIdeas.DTO;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostBusinessDTO {

    private Long id;
    @NotBlank(message = "Title cannot be blank")
    private String title;
    @NotBlank(message = "Category cannot be blank")
    private String category;
    @Size(max = 255, message = "Format cannot exceed 255 characters")
    private String format;
    @NotBlank(message = "Content cannot be blank")
    private String content;
    @Positive(message = "Price must be a positive value")
    private Long price;
    private Long contactInformation;


}
