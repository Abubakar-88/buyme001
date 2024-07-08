package com.skyitschool.skyitschoolecom.dto;

import com.skyitschool.skyitschoolecom.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductReviewData {

    private Product product;
    private long reviewCount;
    private double averageRating;

}
