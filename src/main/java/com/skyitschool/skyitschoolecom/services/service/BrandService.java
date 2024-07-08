package com.skyitschool.skyitschoolecom.services.service;

import com.skyitschool.skyitschoolecom.entity.Brand;
import com.skyitschool.skyitschoolecom.exception.BrandNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BrandService {
    List<Brand> getAllBrands();

    Brand getBrandById(Integer id) throws BrandNotFoundException;

    Brand saveBrand(Brand brand);

    Brand saveBrandWithCategory(Brand brand, List<Integer> categoryIds);

    void deleteBrand(Integer id) throws BrandNotFoundException;

    Brand updateBrand(Integer id, Brand brandDetails) throws BrandNotFoundException;

    Page<Brand> getAllBrandsWithPage(Pageable pageable);

    List<Brand> searchBrands(String keyword);
}
