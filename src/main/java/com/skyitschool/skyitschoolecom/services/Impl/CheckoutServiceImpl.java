package com.skyitschool.skyitschoolecom.services.Impl;

import com.skyitschool.skyitschoolecom.entity.*;
import com.skyitschool.skyitschoolecom.entity.order.Order;
import com.skyitschool.skyitschoolecom.entity.order.OrderDetail;
import com.skyitschool.skyitschoolecom.entity.order.OrderStatus;
import com.skyitschool.skyitschoolecom.orderDTO.CheckoutRequest;
import com.skyitschool.skyitschoolecom.orderDTO.CheckoutResponse;
import com.skyitschool.skyitschoolecom.orderDTO.OrderItemRequest;
import com.skyitschool.skyitschoolecom.repository.*;
import com.skyitschool.skyitschoolecom.services.service.CheckoutService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ShippingRateRepository shippingRateRepository;

    @Transactional
    @Override
    public CheckoutResponse checkout(CheckoutRequest checkoutRequest) {
        Optional<Customer> customerOpt = customerRepository.findById(checkoutRequest.getCustomerId());
        if (!customerOpt.isPresent()) {
            throw new RuntimeException("Customer not found");
        }

        Customer customer = customerOpt.get();
        Order order = createOrder(customer, checkoutRequest);
        order.setStatus(OrderStatus.NEW);  // Set the initial order status to NEW
        orderRepository.save(order);
        Payment payment = processPayment(order, checkoutRequest.getPaymentMethod(), checkoutRequest.getCreditCardNumber());

        return createCheckoutResponse(order, payment);

    }

    private Order createOrder(Customer customer, CheckoutRequest checkoutRequest) {
        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderTime(new Date());
        order.setPaymentMethod(checkoutRequest.getPaymentMethod().toString());
        order.setFirstName(customer.getFirstName());
        order.setLastName(customer.getLastName());
        order.setPhoneNumber(customer.getPhoneNumber());
        order.setAddressLine1(customer.getAddressLine1());
        order.setCity(customer.getCity());
        order.setState(customer.getState());
        order.setPostalCode(customer.getPostalCode());
        order.setCountry(customer.getCountry().getName());
        order.setDeliveryDays(checkoutRequest.getDeliveryDays());
        order.setDeliveryDate(calculateDeliveryDate(order.getOrderTime(), checkoutRequest.getDeliveryDays()));
        order.setOrderNumber(generateOrderNumber());
        List<OrderDetail> orderDetails = new ArrayList<>();
        BigDecimal productCost = BigDecimal.ZERO;
        BigDecimal shippingCost = calculateShippingCost(customer.getCountry(), customer.getState());
        BigDecimal subtotal = BigDecimal.ZERO;

        for (OrderItemRequest itemRequest : checkoutRequest.getOrderItems()) {
            Optional<Product> productOpt = productRepository.findById(itemRequest.getProductId());
            if (!productOpt.isPresent()) {
                throw new RuntimeException("Product not found");
            }

            Product product = productOpt.get();
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProduct(product);
            orderDetail.setQuantity(itemRequest.getQuantity());
            orderDetail.setOrder(order);

            BigDecimal itemProductCost = BigDecimal.valueOf(product.getPrice()).multiply(new BigDecimal(itemRequest.getQuantity()));
            BigDecimal itemShippingCost = shippingCost.multiply(new BigDecimal(itemRequest.getQuantity()));
            BigDecimal itemSubtotal = itemProductCost.add(itemShippingCost);

            productCost = productCost.add(itemProductCost);
            subtotal = subtotal.add(itemSubtotal);

            orderDetail.setProductCost(itemProductCost);
            orderDetail.setShippingCost(shippingCost);
            orderDetail.setSubtotal(itemSubtotal);
            orderDetail.setUnitPrice(product.getPrice());

            orderDetails.add(orderDetail);
        }

        order.setOrderDetails(orderDetails);
        order.setProductCost(productCost);
        order.setShippingCost(shippingCost);
        order.setSubtotal(subtotal);
        order.setTotal(subtotal); // Adjust this if there are any additional fees

        orderRepository.save(order);

        return order;
    }

    private BigDecimal calculateShippingCost(Country country, String state) {
        Optional<ShippingRate> shippingRateOpt = shippingRateRepository.findByCountryAndState(country, state);
        if (!shippingRateOpt.isPresent()) {
            throw new RuntimeException("Shipping rate not found for the specified location");
        }
        return shippingRateOpt.get().getRate();
    }
    private String generateOrderNumber() {
        String orderNumber;
        do {
            orderNumber = String.format("%010d", new Random().nextInt(1000000000));
        } while (orderRepository.existsByOrderNumber(orderNumber));
        return orderNumber;
    }
    private Date calculateDeliveryDate(Date orderTime, int deliveryDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(orderTime);
        calendar.add(Calendar.DAY_OF_MONTH, deliveryDays);
        return calendar.getTime();
    }

    private Payment processPayment(Order order, PaymentMethod paymentMethod, String creditCardNumber) {
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentMethod(paymentMethod);

        if (paymentMethod == PaymentMethod.CREDIT_CARD) {
            if (creditCardNumber == null || creditCardNumber.isEmpty()) {
                throw new RuntimeException("Credit card number is required for credit card payments");
            }
            payment.setCreditCardNumber(creditCardNumber);
            payment.setPaymentStatus("COMPLETED");
        } else if (paymentMethod == PaymentMethod.COD) {
            payment.setPaymentStatus("PENDING");
        }

        paymentRepository.save(payment);
        return payment;
    }

    private CheckoutResponse createCheckoutResponse(Order order, Payment payment) {
        CheckoutResponse response = new CheckoutResponse();
        response.setOrderId(order.getId());
        response.setTotalAmount(order.getTotal());
        response.setOrderStatus(order.getStatus().toString());
        response.setPaymentStatus(payment.getPaymentStatus());
        response.setOrderNumber(order.getOrderNumber());
        response.setMessage("Order placed successfully");
        response.setDeliveryDate(order.getDeliveryDate());

        return response;
    }
}
