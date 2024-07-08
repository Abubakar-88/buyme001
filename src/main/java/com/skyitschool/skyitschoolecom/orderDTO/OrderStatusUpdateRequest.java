package com.skyitschool.skyitschoolecom.orderDTO;

import com.skyitschool.skyitschoolecom.entity.order.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusUpdateRequest {
    private OrderStatus status;
    private String note;
}
