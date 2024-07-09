package com.buyme.buymeEcom.services.Impl;

import com.buyme.buymeEcom.entity.Customer;
import com.buyme.buymeEcom.entity.Product;
import com.buyme.buymeEcom.entity.Review;
import com.buyme.buymeEcom.exception.ProductNotPurchasedException;
import com.buyme.buymeEcom.repository.OrderRepository;
import com.buyme.buymeEcom.repository.ReviewRepository;
import com.buyme.buymeEcom.services.service.ReviewService;
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
