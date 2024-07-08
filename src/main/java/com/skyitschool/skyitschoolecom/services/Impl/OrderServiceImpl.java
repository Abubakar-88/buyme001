package com.skyitschool.skyitschoolecom.services.Impl;

import com.skyitschool.skyitschoolecom.entity.order.Order;
import com.skyitschool.skyitschoolecom.entity.order.OrderStatus;
import com.skyitschool.skyitschoolecom.entity.order.OrderTrack;
import com.skyitschool.skyitschoolecom.orderDTO.OrderDetailResponse;
import com.skyitschool.skyitschoolecom.orderDTO.OrderSearchResponse;
import com.skyitschool.skyitschoolecom.orderDTO.OrderTrackResponse;
import com.skyitschool.skyitschoolecom.repository.OrderRepository;
import com.skyitschool.skyitschoolecom.repository.OrderTrackRepository;
import com.skyitschool.skyitschoolecom.services.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderTrackRepository orderTrackRepository;

    @Override
    public void updateOrderStatus(Integer orderId, OrderStatus newStatus, String note) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (!orderOpt.isPresent()) {
            throw new RuntimeException("Order not found");
        }

        Order order = orderOpt.get();
        order.setStatus(newStatus);
        orderRepository.save(order);

        OrderTrack orderTrack = new OrderTrack();
        orderTrack.setOrder(order);
        orderTrack.setStatus(newStatus);
        orderTrack.setUpdateTime(new Date());
        orderTrack.setNote(note);

        orderTrackRepository.save(orderTrack);
    }

    @Override
    public OrderSearchResponse  findOrderByOrderNumber(String orderNumber) {
        Order order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return mapToOrderSearchResponse(order);
    }
    private OrderSearchResponse mapToOrderSearchResponse(Order order) {
        OrderSearchResponse response = new OrderSearchResponse();
        response.setOrderId(order.getId());
        response.setCustomerId(order.getCustomer().getId());
        response.setCustomerFirstName(order.getCustomer().getFirstName());
        response.setCustomerLastName(order.getCustomer().getLastName());
        response.setPhoneNumber(order.getCustomer().getPhoneNumber());
        response.setAddressLine1(order.getCustomer().getAddressLine1());
        response.setCity(order.getCustomer().getCity());
        response.setState(order.getCustomer().getState());
        response.setCountry(order.getCustomer().getCountry().getName());
        response.setOrderTime(order.getOrderTime());
        response.setPaymentMethod(order.getPaymentMethod().toString());
        response.setProductCost(order.getProductCost());
        response.setShippingCost(order.getShippingCost());
        response.setSubtotal(order.getSubtotal());
        response.setTotal(order.getTotal());
        response.setStatus(order.getStatus().toString());
        response.setDeliveryDays(order.getDeliveryDays());
        response.setDeliveryDate(order.getDeliveryDate());

        List<OrderDetailResponse> orderDetailResponses = order.getOrderDetails().stream().map(detail -> {
            OrderDetailResponse detailResponse = new OrderDetailResponse();
            detailResponse.setProductId(detail.getProduct().getId());
            detailResponse.setProductName(detail.getProduct().getName());
            detailResponse.setProductCost(detail.getProductCost());
            detailResponse.setQuantity(detail.getQuantity());
            detailResponse.setShippingCost(detail.getShippingCost());
            detailResponse.setSubtotal(detail.getSubtotal());
            detailResponse.setUnitPrice(detail.getUnitPrice());
            return detailResponse;
        }).collect(Collectors.toList());

        response.setOrderDetails(orderDetailResponses);

        return response;
    }
    @Override
    public List<OrderSearchResponse> getAllOrders(int page, int size, List<String> sortFields, List<Sort.Direction> directions) {
        List<Sort.Order> orders = new ArrayList<>();
        for (int i = 0; i < sortFields.size(); i++) {
            orders.add(new Sort.Order(directions.get(i), sortFields.get(i)));
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
        List<Order> orderlist = orderRepository.findAll(pageable).getContent();
        return orderlist.stream()
                .map(this::mapToOrderSearchResponse)
                .collect(Collectors.toList());
    }
    @Override
    public Order updateOrder(Integer orderId, Order updatedOrder) {
        return orderRepository.findById(orderId)
                .map(order -> {
                    order.setOrderTime(updatedOrder.getOrderTime());
                    order.setPaymentMethod(updatedOrder.getPaymentMethod());
                    order.setProductCost(updatedOrder.getProductCost());
                    order.setShippingCost(updatedOrder.getShippingCost());
                    order.setSubtotal(updatedOrder.getSubtotal());
                    order.setTotal(updatedOrder.getTotal());
                    order.setStatus(updatedOrder.getStatus());
                    order.setFirstName(updatedOrder.getFirstName());
                    order.setLastName(updatedOrder.getLastName());
                    order.setPhoneNumber(updatedOrder.getPhoneNumber());
                    order.setAddressLine1(updatedOrder.getAddressLine1());
                    order.setCity(updatedOrder.getCity());
                    order.setState(updatedOrder.getState());
                    order.setCountry(updatedOrder.getCountry());
                    order.setDeliveryDays(updatedOrder.getDeliveryDays());
                    order.setDeliveryDate(updatedOrder.getDeliveryDate());
                    return orderRepository.save(order);
                })
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public void deleteOrder(Integer orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new RuntimeException("Order not found");
        }
        orderRepository.deleteById(orderId);
    }
}
