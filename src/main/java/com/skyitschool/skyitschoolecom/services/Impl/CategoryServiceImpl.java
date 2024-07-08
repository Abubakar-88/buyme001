package com.skyitschool.skyitschoolecom.services.Impl;

import com.skyitschool.skyitschoolecom.repository.BrandRepository;
import com.skyitschool.skyitschoolecom.exception.CategoryNotFoundException;
import com.skyitschool.skyitschoolecom.entity.Category;
import com.skyitschool.skyitschoolecom.repository.CategoryRepository;
import com.skyitschool.skyitschoolecom.services.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    @Autowired
    private BrandRepository brandRepository;
    //    public List<Category> getAllCategories() {
//        return categoryRepository.findAll();
//    }
    public Page<Category> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }
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
