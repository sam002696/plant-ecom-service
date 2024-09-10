package com.sami.plant_ecom.controller;

import com.sami.plant_ecom.dto.ReviewRequest;
import com.sami.plant_ecom.responses.ReviewResponse;
import com.sami.plant_ecom.service.interfaces.IReviewService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.sami.plant_ecom.utils.ResponseBuilder.success;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@Tag(name = "Review API")
@RequestMapping("/api/v1/review")
public class ReviewController {


    @Autowired
    private IReviewService reviewService;


    @PostMapping("/add")
    public ResponseEntity<JSONObject> addReview(@RequestBody ReviewRequest reviewRequest) {
        ReviewResponse reviewResponse = reviewService.addReview(reviewRequest);
        return ok(success(reviewResponse, "Review has been added successfully").getJson());
    }


}
