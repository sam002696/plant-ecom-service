package com.sami.plant_ecom.service.interfaces;

import com.sami.plant_ecom.dto.ReviewRequest;
import com.sami.plant_ecom.responses.ReviewResponse;

public interface IReviewService {

 ReviewResponse addReview(ReviewRequest reviewRequest);

}
