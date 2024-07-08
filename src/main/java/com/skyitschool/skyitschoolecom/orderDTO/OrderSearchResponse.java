package com.skyitschool.skyitschoolecom.orderDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderSearchResponse {
    private Integer orderId;
    private Integer customerId;
    private String customerFirstName;
    private String customerLastName;
    private String phoneNumber;
    private String addressLine1;
    private String city;
    private String state;
    private String country;
    private Date orderTime;
    private String paymentMethod;
    private BigDecimal productCost;
    private BigDecimal shippingCost;
    private BigDecimal subtotal;
    private BigDecimal total;
    private String status;
    private int deliveryDays;
    private Date deliveryDate;
    private List<OrderDetailResponse> orderDetails;
}
