package com.buyme.buymeEcom.services.Impl;

import com.buyme.buymeEcom.dto.CategoryDTO;
import com.buyme.buymeEcom.dto.ProductDTO;
import com.buyme.buymeEcom.entity.Product;
import com.buyme.buymeEcom.repository.BrandRepository;
import com.buyme.buymeEcom.exception.CategoryNotFoundException;
import com.buyme.buymeEcom.entity.Category;
import com.buyme.buymeEcom.repository.CategoryRepository;
import com.buyme.buymeEcom.services.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BrandRepository brandRepository;
    //    public List<Category> getAllCategories() {
//        return categoryRepository.findAll();
//    }

    @Override
    public Page<CategoryDTO> getAllCategories(Pageable pageable) {
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        return categoryPage.map(this::convertToDTO);
    }

    private CategoryDTO convertToDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setEnabled(category.isEnabled());
        dto.setProducts(category.getProducts().stream().map(this::convertToProductDTO).collect(Collectors.toList()));
        return dto;
    }

    private ProductDTO convertToProductDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setCreatedTime(product.getCreatedTime());
        dto.setUpdatedTime(product.getUpdatedTime());
        dto.setEnabled(product.isEnabled());
        dto.setInStock(product.isInStock());
        dto.setPrice(product.getPrice());
        dto.setCost(product.getCost());
        dto.setDiscountPercent(product.getDiscountPercent());
        dto.setMainImage(product.getMainImage());
        return dto;
    }
//    public Page<Category> getAllCategories(Pageable pageable) {
//        return categoryRepository.findAll(pageable);
//    }
    @Override
    public Category getCategoryById(Integer id) throws CategoryNotFoundException {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        return optionalCategory.orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + id));
    }
    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Integer id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category updateCategory(Integer id, Category updatedCategory) throws CategoryNotFoundException {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            Category existingCategory = optionalCategory.get();
            existingCategory.setName(updatedCategory.getName());

            return categoryRepository.save(existingCategory);
        } else {
            throw new CategoryNotFoundException("Category not found with ID: " + id);
        }
    }

    @Override
    public List<Category> searchCategories(String keyword) {
        return categoryRepository.findByNameContainingIgnoreCase(keyword);
    }





}
