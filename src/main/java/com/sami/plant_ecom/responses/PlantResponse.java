package com.sami.plant_ecom.responses;

import com.sami.plant_ecom.entity.Plant;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class PlantResponse implements Serializable {

    private Long id;
    private String plantName;
    private double sold;
    private double rating;
    private String plantDesc;
    private int quantity;
    private boolean favorite;
    private String category;
    private List<ReviewResponse> reviewResponse;

    // Static factory method to convert Plant entity to PlantResponse DTO
    public static PlantResponse select(Plant plant) {
        if (plant == null) {
            return null;
        }

        PlantResponse response = new PlantResponse();

        response.setId(plant.getId());
        response.setPlantName(plant.getPlantName());
        response.setSold(plant.getSold());
        response.setRating(plant.getRating());
        response.setPlantDesc(plant.getPlantDesc());
        response.setQuantity(plant.getQuantity());
        response.setFavorite(plant.isFavorite());
        response.setCategory(plant.getCategory());

        if(plant.getReviews() != null){
            response.setReviewResponse(plant.getReviews().stream().map(ReviewResponse::selectReviewWithoutPlantName)
                    .collect(Collectors.toList()));
        }


        return response;
    }
}
