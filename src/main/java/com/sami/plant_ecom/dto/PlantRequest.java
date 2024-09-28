package com.sami.plant_ecom.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PlantRequest {

    @NotBlank(message = "Plant name is required")
    private String plantName;

    @Min(value = 0, message = "Sold value cannot be negative")
    private double sold;

    @Min(value = 0, message = "Rating must be at least 0")
    @Max(value = 5, message = "Rating cannot be more than 5")
    private double rating;

//    @Min(value = 0, message = "Reviews count cannot be negative")
//    private int reviews;

    @NotBlank(message = "Plant price is required")
    private double price;

    @NotBlank(message = "Plant description is required")
    private String plantDesc;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    private boolean favorite;

    @NotBlank(message = "Category is required")
    private String category;

    @NotBlank(message = "Plant image is required")
    private String plantImageUrl;
}
