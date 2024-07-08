package com.skyitschool.skyitschoolecom.orderDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutResponse {
    private Integer orderId;
    private BigDecimal totalAmount;
    private String orderStatus;
    private String paymentStatus;
    private String message;
    private String orderNumber;
    private Date deliveryDate;

}
