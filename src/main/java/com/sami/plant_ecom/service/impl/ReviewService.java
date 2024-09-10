package com.sami.plant_ecom.service.impl;

import com.sami.plant_ecom.dto.ReviewRequest;
import com.sami.plant_ecom.entity.Plant;
import com.sami.plant_ecom.entity.Review;
import com.sami.plant_ecom.entity.User;
import com.sami.plant_ecom.exceptions.CustomMessageException;
import com.sami.plant_ecom.repository.PlantRepository;
import com.sami.plant_ecom.repository.ReviewRepository;
import com.sami.plant_ecom.repository.UserRepository;
import com.sami.plant_ecom.responses.ReviewResponse;
import com.sami.plant_ecom.security.UserPrincipal;
import com.sami.plant_ecom.service.interfaces.IReviewService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService implements IReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlantRepository plantRepository;

    @Override
    public ReviewResponse addReview(ReviewRequest reviewRequest) {
        // Get the logged-in user (verified user)
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userPrincipal.getId();

        // Fetch the user and plant from the database
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomMessageException("Logged-in user not found"));
        Plant plant = plantRepository.findById(reviewRequest.getPlantId())
                .orElseThrow(() -> new CustomMessageException("Plant not found"));

        // Create and save the new review
        Review review = new Review();
        review.setUser(user);
        review.setPlant(plant);
        review.setComment(reviewRequest.getComment());
        review.setRating(reviewRequest.getRating());

        Review savedReview = reviewRepository.save(review);

        // Return the review response
        return ReviewResponse.selectReview(savedReview);
    }
}
