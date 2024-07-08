package com.skyitschool.skyitschoolecom.controller;


import com.skyitschool.skyitschoolecom.exception.BrandNotFoundException;
import com.skyitschool.skyitschoolecom.services.service.BrandService;
import com.skyitschool.skyitschoolecom.services.service.CategoryService;
import com.skyitschool.skyitschoolecom.entity.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryService categoryService;


//    @GetMapping
//    public List<Brand> getAllBrands() {
//        return brandService.getAllBrands();
//    }

//    @GetMapping
//    public Page<Brand> getAllBrands(Pageable pageable) {
//      //  return brandService.getAllBrands(pageable);
//        pageable = PageRequest.of(0, pageable.getPageSize(), pageable.getSort());
//        return brandService.getAllBrands(pageable);
//
//
//    }
@GetMapping
public ResponseEntity<Page<Brand>> getAllBrands(
        @RequestParam(defaultValue = "5") int pageSize,
        @RequestParam(defaultValue = "1") int pageNumber) {

    Pageable pageable = PageRequest.of(pageNumber-1, pageSize);
    Page<Brand> brands = brandService.getAllBrandsWithPage(pageable);

    if (brands.isEmpty()) {
        return ResponseEntity.noContent().build();
    } else {
        return ResponseEntity.ok(brands);
    }
}
    @GetMapping("/{id}")
    public Brand getBrandById(@PathVariable Integer id) throws BrandNotFoundException {
        return brandService.getBrandById(id);
    }

    @PostMapping
    public ResponseEntity<Brand> addBrand(@RequestBody Brand brand) {
        Brand savedBrand = brandService.saveBrand(brand);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBrand);
    }
//    @PostMapping("/brands-with-category")
//    public ResponseEntity<Brand> createBrandWithCategories(@RequestBody CategoryBrandDTO brandCategoryDTO) {
//
//
//            Brand brand = new Brand();
//            brand.setName(brandCategoryDTO.getBrandName());
//            brand.setLogo(brandCategoryDTO.getBrandLogo());
//            // Set other properties of the brand as needed
//
//            Integer categoryIds = brandCategoryDTO.getCategoryId();
//            Brand savedBrand = brandService.saveBrandWithCategories(brand, categoryIds);
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(savedBrand);
//
//    }

    @PostMapping("/brands-with-category")
    public ResponseEntity<Brand> saveBrandWithCategory(@RequestBody Brand brand,
                                                       @RequestParam("categoryIds") List<Integer> categoryIds) {
        Brand savedBrand = brandService.saveBrandWithCategory(brand, categoryIds);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBrand);

    }


    @PutMapping("/{id}")
    public Brand updateBrand(@PathVariable Integer id, @RequestBody Brand brand) throws BrandNotFoundException {
        brand.setId(id);
        return brandService.updateBrand(id, brand);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBrand(@PathVariable Integer id) {
        try {
            brandService.deleteBrand(id);
            String message = "Brand with ID: " + id + " has been deleted successfully.";
            return ResponseEntity.noContent().build();
        } catch (BrandNotFoundException ex) {
            String errorMessage = "Brand with ID: " + id + " not found.";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }
    @GetMapping("/search")
    public ResponseEntity<List<Brand>> searchBrands(@RequestParam("keyword") String keyword) {
        List<Brand> searchResults = brandService.searchBrands(keyword);

        if (searchResults.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(searchResults);
        }
    }

}