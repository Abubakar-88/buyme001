package com.buyme.buymeEcom.orderDTO;


import com.buyme.buymeEcom.entity.PaymentMethod;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutRequest {
    private Integer customerId;
    private PaymentMethod paymentMethod;
    private String creditCardNumber;
    private int deliveryDays;
    private List<OrderItemRequest> orderItems;


}
