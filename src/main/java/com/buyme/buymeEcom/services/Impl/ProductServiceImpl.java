package com.buyme.buymeEcom.services.Impl;

import com.buyme.buymeEcom.dto.ProductReviewData;
import com.buyme.buymeEcom.exception.CategoryNotFoundException;
import com.buyme.buymeEcom.repository.ReviewRepository;
import com.buyme.buymeEcom.services.service.BrandService;
import com.buyme.buymeEcom.services.service.CategoryService;
import com.buyme.buymeEcom.entity.Product;
import com.buyme.buymeEcom.exception.ProductNotFoundException;
import com.buyme.buymeEcom.repository.ProductRepository;
import com.buyme.buymeEcom.services.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    @Autowired
    private BrandService brandService;

    @Autowired private CategoryService categoryService;

    @Autowired
    private ReviewRepository reviewRepository;


    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public List<Product> listAllProducts(){
        return   productRepository.findAll();
    }
    @Override
    public List<Product> listProductsByPage(int pageNum, int pageSize, List<String> sortFields, List<Sort.Direction> directions) {
        List<Sort.Order> orders = new ArrayList<>();
        for (int i = 0; i < sortFields.size(); i++) {
            orders.add(new Sort.Order(directions.get(i), sortFields.get(i)));
        }
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by(orders));
        return productRepository.findAll(pageable).getContent();
    }
    @Override
    public List<ProductReviewData> listProductsByReviewRatings(int pageNum, int pageSize) {
        List<Product> allProducts = productRepository.findAll();
        List<ProductReviewData> productReviewDataList = allProducts.stream().map(product -> {
            long reviewCount = reviewRepository.countByProduct(product.getId());
            Double averageRating = reviewRepository.findAverageRatingByProduct(product.getId());
            averageRating = averageRating != null ? averageRating : 0.0;
            return new ProductReviewData(product, reviewCount, averageRating);
        }).collect(Collectors.toList());

        // Sort products based on review count and average rating
        productReviewDataList.sort(Comparator
                .comparing(ProductReviewData::getReviewCount).reversed()
                .thenComparing(ProductReviewData::getAverageRating).reversed());

        // Apply pagination manually
        int start = Math.min((pageNum - 1) * pageSize, productReviewDataList.size());
        int end = Math.min(start + pageSize, productReviewDataList.size());

        return productReviewDataList.subList(start, end);
    }
    @Override
    public Product saveProduct(Product product) {
        if (product.getCreatedTime() == null) {
            product.setCreatedTime(Date.from(Instant.now()));
        }

        // Set the updated time to the current time
        product.setUpdatedTime(Date.from(Instant.now()));
        return productRepository.save(product);
    }
    @Override
    public Product updateProduct(Integer id, Product product) throws ProductNotFoundException {
        // Your logic to update an existing product
        Optional<Product> optionalProduct = productRepository.findById(id);


        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();
            Date createdTime = existingProduct.getCreatedTime();
            existingProduct.setName(product.getName());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setCreatedTime(createdTime);
            existingProduct.setUpdatedTime(Date.from(Instant.now()));
            existingProduct.setPrice(product.getPrice());
            existingProduct.setCategory(product.getCategory());
            existingProduct.setBrand(product.getBrand());
            existingProduct.setCost(product.getCost());
            existingProduct.setInStock(product.isInStock());
            existingProduct.setEnabled(product.isEnabled());
            existingProduct.setMainImage(product.getMainImage());

            return productRepository.save(existingProduct);
        } else {
            throw new ProductNotFoundException("Product not found with ID: " + id);
        }
    }

    @Override
    public Product getProductById(Integer productId) throws ProductNotFoundException {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            return optionalProduct.get();
        } else {
            throw new ProductNotFoundException("Product not found with ID: " + productId);
        }
    }

    @Override
    public void deleteProduct(Integer productId) throws ProductNotFoundException {
        // Check if the product exists
        if (!productRepository.existsById(productId)) {
            throw new ProductNotFoundException("Product not found with ID: " + productId);
        }

        // Delete the product
        productRepository.deleteById(productId);
    }


    @Override
    public List<Product> searchProductsByName(String name) {
        // Use the productRepository or your desired data access method to perform the search
        return productRepository.findByNameContainingIgnoreCase(name);
    }
    @Override
    public List<Product> getProductsByCategory(Integer categoryId) throws CategoryNotFoundException {


        categoryService.getCategoryById(categoryId); // Validate category existence

        return productRepository.findByCategoryId(categoryId);
    }



}
