package com.buyme.buymeEcom.orderDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponse {
    private Integer id;
    private Integer customerId;
    private Integer productId;
    private int rating;
    private String comment;
    private Date reviewDate;


}
