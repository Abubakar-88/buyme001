package com.skyitschool.skyitschoolecom.dto;

import jakarta.persistence.Entity;
import lombok.*;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Integer id;
    private String name;
    private String description;
    private Date createdTime;
    private Date updatedTime;
    private boolean enabled;
    private boolean inStock;
    private float price;
    private float cost;
    private float discountPercent;
    private String mainImage;
}
