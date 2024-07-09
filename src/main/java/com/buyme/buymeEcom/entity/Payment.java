package com.buyme.buymeEcom.entity;

import com.buyme.buymeEcom.entity.order.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private String creditCardNumber;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private String paymentStatus;
}
