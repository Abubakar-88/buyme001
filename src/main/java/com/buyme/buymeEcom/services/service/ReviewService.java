package com.buyme.buymeEcom.services.service;

import com.buyme.buymeEcom.entity.Review;

import java.util.List;

public interface ReviewService {
    Review addReview(Integer customerId, Integer productId, int rating, String comment);
     List<Review> getReviewsByProduct(Integer productId);
}
