package com.buyme.buymeEcom.controller;

import com.buyme.buymeEcom.entity.order.Order;
import com.buyme.buymeEcom.orderDTO.OrderSearchResponse;
import com.buyme.buymeEcom.orderDTO.OrderStatusUpdateRequest;
import com.buyme.buymeEcom.services.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/{orderId}/status")
    public ResponseEntity<String> updateOrderStatus(@PathVariable Integer orderId, @RequestBody OrderStatusUpdateRequest request) {
        orderService.updateOrderStatus(orderId, request.getStatus(), request.getNote());
        return new ResponseEntity<>("Order status updated", HttpStatus.OK);
    }
//GET "http://localhost:8080/api/orders/search?orderNumber=1234567890"
    @GetMapping("/search")
    public ResponseEntity<OrderSearchResponse> searchOrderByOrderNumber(@RequestParam String orderNumber) {
        try {
            OrderSearchResponse order = orderService.findOrderByOrderNumber(orderNumber);
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @GetMapping
    public ResponseEntity<List<OrderSearchResponse>> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) List<String> sortBy,
            @RequestParam(required = false) List<Sort.Direction> direction) {

        if (sortBy == null || sortBy.isEmpty()) {
            sortBy = Arrays.asList("orderId"); // Default sort field
        }
        if (direction == null || direction.isEmpty()) {
            direction = Arrays.asList(Sort.Direction.ASC); // Default sort direction
        }

        List<OrderSearchResponse> orderResponses = orderService.getAllOrders(page, size, sortBy, direction);
        return ResponseEntity.ok(orderResponses);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable Integer orderId, @RequestBody Order updatedOrder) {
        Order order = orderService.updateOrder(orderId, updatedOrder);
        return ResponseEntity.ok(order);
    }

}
