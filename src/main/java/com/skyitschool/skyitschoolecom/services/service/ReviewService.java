package com.skyitschool.skyitschoolecom.services.service;

import com.skyitschool.skyitschoolecom.entity.Review;

import java.util.List;

public interface ReviewService {
    Review addReview(Integer customerId, Integer productId, int rating, String comment);
     List<Review> getReviewsByProduct(Integer productId);
}
