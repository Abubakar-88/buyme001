package com.buyme.buymeEcom.services.Impl;

import com.buyme.buymeEcom.entity.Brand;
import com.buyme.buymeEcom.entity.Category;
import com.buyme.buymeEcom.exception.BrandNotFoundException;
import com.buyme.buymeEcom.repository.BrandRepository;
import com.buyme.buymeEcom.repository.CategoryRepository;
import com.buyme.buymeEcom.services.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BrandServiceImpl implements BrandService {


    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    @Override
    public Brand getBrandById(Integer id) throws BrandNotFoundException {
        return brandRepository.findById(id)
                .orElseThrow(() -> new BrandNotFoundException("Brand with ID: " + id + " not found"));
    }

    @Override
    public Brand saveBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public Brand saveBrandWithCategory(Brand brand, List<Integer> categoryIds) {
        Set<Category> categories = new HashSet<>();

        for (Integer categoryId : categoryIds) {
            Category category = categoryRepository.findById(categoryId).orElse(null);
            if (category != null) {
                categories.add(category);
            }
        }

        brand.setCategories(categories);
        return brandRepository.save(brand);
    }

    @Override
    public void deleteBrand(Integer id) throws BrandNotFoundException {
        if (!brandRepository.existsById(id)) {
            throw new BrandNotFoundException("Brand with ID: " + id + " not found");
        }

        brandRepository.deleteById(id);
    }

    @Override
    public Brand updateBrand(Integer id, Brand brandDetails) throws BrandNotFoundException {
        Brand brand = getBrandById(id);
        brand.setName(brandDetails.getName());
        // Set any other properties you want to update
        brand.setLogo(brandDetails.getLogo());
        return brandRepository.save(brand);
    }
    @Override
   public Page<Brand> getAllBrandsWithPage(Pageable pageable){
        return brandRepository.findAll(pageable);
    };


    @Override
    public List<Brand> searchBrands(String keyword) {
        return brandRepository.findByNameContainingIgnoreCase(keyword);
    }
}