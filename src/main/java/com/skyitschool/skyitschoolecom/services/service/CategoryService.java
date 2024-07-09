package com.skyitschool.skyitschoolecom.services.service;

import com.skyitschool.skyitschoolecom.dto.CategoryDTO;
import com.skyitschool.skyitschoolecom.exception.CategoryNotFoundException;
import com.skyitschool.skyitschoolecom.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface CategoryService {
    Page<CategoryDTO> getAllCategories(Pageable pageable);
    Category getCategoryById(Integer id) throws CategoryNotFoundException;
    Category saveCategory(Category category);
    void deleteCategory(Integer id);
    Category updateCategory(Integer id, Category updatedCategory) throws CategoryNotFoundException;
    List<Category> searchCategories(String keyword);




//    public Category getCategoryById(Set<Category> categories, Integer categoryId) {
//        for (Category category : categories) {
//            if (category.getId().equals(categoryId)) {
//                return category;
//            }
//        }
//        return null;
//    }
}
