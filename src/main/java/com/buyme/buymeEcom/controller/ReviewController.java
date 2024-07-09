package com.buyme.buymeEcom.controller;

import com.buyme.buymeEcom.entity.Review;
import com.buyme.buymeEcom.exception.ProductNotPurchasedException;
import com.buyme.buymeEcom.orderDTO.ReviewRequestBody;
import com.buyme.buymeEcom.orderDTO.ReviewResponse;
import com.buyme.buymeEcom.exception.DetailedErrorResponse;
import com.buyme.buymeEcom.services.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<?> addReview(@RequestBody ReviewRequestBody reviewDTO) {
        try {
            Review review = reviewService.addReview(
                    reviewDTO.getCustomerId(),
                    reviewDTO.getProductId(),
                    reviewDTO.getRating(),
                    reviewDTO.getComment()
            );

            ReviewResponse reviewResponse = new ReviewResponse(
                    review.getId(),
                    review.getCustomer().getId(),
                    review.getProduct().getId(),
                    review.getRating(),
                    review.getComment(),
                    review.getReviewDate()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(reviewResponse);
        }catch (ProductNotPurchasedException e) {
            DetailedErrorResponse errorResponse = new DetailedErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewResponse>> getReviewsByProduct(@PathVariable Integer productId) {
        List<Review> reviews = reviewService.getReviewsByProduct(productId);
        List<ReviewResponse> reviewResponses = reviews.stream()
                .map(review -> new ReviewResponse(
                        review.getId(),
                        review.getCustomer().getId(),
                        review.getProduct().getId(),
                        review.getRating(),
                        review.getComment(),
                        review.getReviewDate()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(reviewResponses);
    }
}
