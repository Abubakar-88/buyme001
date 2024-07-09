package com.buyme.buymeEcom.entity.order;

import com.buyme.buymeEcom.entity.AbstractAddress;
import com.buyme.buymeEcom.entity.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends AbstractAddress {

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "order_number", nullable = false, length = 45)
    private String orderNumber;


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Date orderTime;
    private String paymentMethod;
    private BigDecimal productCost;
    private BigDecimal shippingCost;  // Add this field
    private BigDecimal subtotal;
    private BigDecimal total;


    @Column(nullable = false, length = 45)
    protected String state;

    @Column(nullable = false, length = 45)
    private String country;

    private int deliveryDays;

    private Date deliveryDate;
}
