package com.skyitschool.skyitschoolecom.services.service;

import com.skyitschool.skyitschoolecom.entity.order.Order;
import com.skyitschool.skyitschoolecom.entity.order.OrderStatus;
import com.skyitschool.skyitschoolecom.orderDTO.OrderSearchResponse;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


public interface OrderService {
     void updateOrderStatus(Integer orderId, OrderStatus newStatus, String note);
     OrderSearchResponse findOrderByOrderNumber(String orderNumber);
    // List<OrderSearchResponse> getAllOrders();
    List<OrderSearchResponse> getAllOrders(int page, int size, List<String> sortFields, List<Sort.Direction> directions);
     void deleteOrder(Integer orderId);

     Order updateOrder(Integer orderId, Order updatedOrder);
}
