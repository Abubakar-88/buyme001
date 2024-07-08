package com.skyitschool.skyitschoolecom.services.Impl;

import com.skyitschool.skyitschoolecom.entity.Customer;
import com.skyitschool.skyitschoolecom.entity.Product;
import com.skyitschool.skyitschoolecom.entity.Review;
import com.skyitschool.skyitschoolecom.exception.ProductNotPurchasedException;
import com.skyitschool.skyitschoolecom.repository.OrderRepository;
import com.skyitschool.skyitschoolecom.repository.ReviewRepository;
import com.skyitschool.skyitschoolecom.services.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Review addReview(Integer customerId, Integer productId, int rating, String comment) {
        // Check if the customer has purchased the product
        boolean hasPurchased = orderRepository.existsByCustomerIdAndProductId(customerId, productId);

        if (!hasPurchased) {
            throw new ProductNotPurchasedException("Customer has not purchased this product");
        }

        Review review = new Review();
        review.setCustomer(new Customer(customerId));
        review.setProduct(new Product(productId));
        review.setRating(rating);
        review.setComment(comment);
        review.setReviewDate(new Date());

        return reviewRepository.save(review);
    }



    @Override
    public List<Review> getReviewsByProduct(Integer productId) {
        return reviewRepository.findByProductId(productId);
    }


}
