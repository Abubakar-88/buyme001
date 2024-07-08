package com.skyitschool.skyitschoolecom.orderDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequestBody {
    private Integer customerId;
    private Integer productId;
    private int rating;
    private String comment;
}
