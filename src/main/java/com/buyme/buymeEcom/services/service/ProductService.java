package com.buyme.buymeEcom.services.service;

import com.buyme.buymeEcom.dto.ProductReviewData;
import com.buyme.buymeEcom.entity.Product;
import com.buyme.buymeEcom.exception.CategoryNotFoundException;
import com.buyme.buymeEcom.exception.ProductNotFoundException;
import org.springframework.data.domain.Sort;

import java.util.List;


public interface ProductService {

    List<Product> listAllProducts();
    public List<Product> listProductsByPage(int pageNum, int pageSize, List<String> sortFields, List<Sort.Direction> directions);
    List<ProductReviewData> listProductsByReviewRatings(int pageNum, int pageSize);
    Product saveProduct(Product product);
    Product updateProduct(Integer id, Product product) throws ProductNotFoundException;
    Product getProductById(Integer productId) throws ProductNotFoundException;
    void deleteProduct(Integer productId) throws ProductNotFoundException;
    List<Product> searchProductsByName(String name);
    List<Product> getProductsByCategory(Integer categoryId) throws CategoryNotFoundException;





}
