package com.skyitschool.skyitschoolecom.controller;

import com.skyitschool.skyitschoolecom.dto.ProductReviewData;
import com.skyitschool.skyitschoolecom.exception.BrandNotFoundException;
import com.skyitschool.skyitschoolecom.exception.CategoryNotFoundException;
import com.skyitschool.skyitschoolecom.services.service.BrandService;
import com.skyitschool.skyitschoolecom.services.service.CategoryService;
import com.skyitschool.skyitschoolecom.entity.Brand;
import com.skyitschool.skyitschoolecom.entity.Category;
import com.skyitschool.skyitschoolecom.entity.Product;
import com.skyitschool.skyitschoolecom.exception.ProductNotFoundException;
import com.skyitschool.skyitschoolecom.services.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;
    private final BrandService brandService;
    private final CategoryService categoryService;

    @Autowired
    public ProductController(ProductService productService, BrandService brandService, CategoryService categoryService) {
        this.productService = productService;
        this.brandService = brandService;
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
       public List<Product> listProduct(){
        return productService.listAllProducts();
       }
    @GetMapping("/page/{pageNum}")
    public ResponseEntity<List<Product>> listByPage(
            @PathVariable(name = "pageNum") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) List<String> sortBy,
            @RequestParam(required = false) List<Sort.Direction> direction) {

        if (sortBy == null || sortBy.isEmpty()) {
            sortBy = List.of("name"); // Default sort field
        }
        if (direction == null || direction.isEmpty()) {
            direction = List.of(Sort.Direction.ASC); // Default sort direction
        }

        List<Product> productList = productService.listProductsByPage(pageNum, pageSize, sortBy, direction);
        return ResponseEntity.ok(productList);
    }

    @GetMapping("/page/{pageNum}/by-reviews")
    public ResponseEntity<List<ProductReviewData>> listByReviews(
            @PathVariable(name = "pageNum") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {

        List<ProductReviewData> productList = productService.listProductsByReviewRatings(pageNum, pageSize);
        return ResponseEntity.ok(productList);
    }


    @PostMapping("/new")
    public ResponseEntity<Product> saveProduct(@RequestBody Product product,
                                               @RequestParam("brandId") Integer brandId,
                                               @RequestParam("categoryId") Integer categoryId) {

        try {
            // Get the brand and category by their IDs
            Brand brand = brandService.getBrandById(brandId);
            Category category = categoryService.getCategoryById(categoryId);

            // Set the brand and category for the product
            product.setBrand(brand);
            product.setCategory(category);

            // Save the product
            Product savedProduct = productService.saveProduct(product);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
        } catch (CategoryNotFoundException | BrandNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Integer id, @RequestBody Product product) throws ProductNotFoundException {
        Product createdProduct = productService.updateProduct(id, product);
        return ResponseEntity.status(HttpStatus.OK).body(createdProduct);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Integer productId) {
        try {
            Product product = productService.getProductById(productId);
            return ResponseEntity.ok(product);
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable("productId") Integer productId) {
        try {
            productService.deleteProduct(productId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Product Delete Successfully");
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam("name") String name) {
        List<Product> products = productService.searchProductsByName(name);
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return ResponseEntity.ok(products);
        }
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable("categoryId") Integer categoryId) {
        try {
            List<Product> products = productService.getProductsByCategory(categoryId);
            if (products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            } else {
                return ResponseEntity.ok(products);
            }
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
