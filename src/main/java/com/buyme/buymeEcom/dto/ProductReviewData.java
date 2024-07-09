package com.buyme.buymeEcom.dto;

import com.buyme.buymeEcom.entity.Product;
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
