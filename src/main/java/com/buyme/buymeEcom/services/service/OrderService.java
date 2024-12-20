package com.buyme.buymeEcom.services.service;

import com.buyme.buymeEcom.entity.order.Order;
import com.buyme.buymeEcom.entity.order.OrderStatus;
import com.buyme.buymeEcom.orderDTO.OrderSearchResponse;
import org.springframework.data.domain.Sort;

import java.util.List;


public interface OrderService {
     void updateOrderStatus(Integer orderId, OrderStatus newStatus, String note);
     OrderSearchResponse findOrderByOrderNumber(String orderNumber);
    // List<OrderSearchResponse> getAllOrders();
    List<OrderSearchResponse> getAllOrders(int page, int size, List<String> sortFields, List<Sort.Direction> directions);
     void deleteOrder(Integer orderId);

     Order updateOrder(Integer orderId, Order updatedOrder);
}
