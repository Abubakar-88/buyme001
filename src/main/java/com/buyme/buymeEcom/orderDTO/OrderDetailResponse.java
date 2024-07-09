package com.buyme.buymeEcom.orderDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailResponse {
    private Integer productId;
    private String productName;
    private BigDecimal productCost;
    private int quantity;
    private BigDecimal shippingCost;
    private BigDecimal subtotal;
    private float unitPrice;


}