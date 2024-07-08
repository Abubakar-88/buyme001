package com.skyitschool.skyitschoolecom.repository;

import com.skyitschool.skyitschoolecom.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Integer> {
//    @Query("SELECT DISTINCT b FROM Brand b LEFT JOIN FETCH b.categories")
//    List<Brand> findAllBrandsWithCategories();

    @Query("SELECT b FROM Brand b WHERE b.name LIKE %?1%")
    public List<Brand> findByNameContainingIgnoreCase(String keyword);

}
