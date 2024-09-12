package com.sami.plant_ecom.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sami.plant_ecom.entity.Review;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewResponse {
    private Long reviewId;
    private String comment;
    private int rating;
    private Long userId;
    private String userName;
    private Long plantId;
    private String plantName;

    // Static method to convert a Review entity to ReviewResponse DTO
    public static ReviewResponse selectReview(Review review) {
        ReviewResponse response = new ReviewResponse();
        response.setReviewId(review.getId());
        response.setComment(review.getComment());
        response.setRating(review.getRating());
        response.setUserId(review.getUser().getId());
        response.setUserName(review.getUser().getName());
        response.setPlantId(review.getPlant().getId());
        response.setPlantName(review.getPlant().getPlantName());
        return response;
    }

    public static ReviewResponse selectReviewWithoutPlantName(Review review) {
        ReviewResponse response = new ReviewResponse();
        response.setReviewId(review.getId());
        response.setComment(review.getComment());
        response.setRating(review.getRating());
        response.setUserId(review.getUser().getId());
        response.setUserName(review.getUser().getName());
        return response;
    }
}
