package com.skyitschool.skyitschoolecom.repository;

import com.skyitschool.skyitschoolecom.entity.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    boolean existsByOrderNumber(String orderNumber);
    Optional<Order> findByOrderNumber(String orderNumber);

    @Query("SELECT CASE WHEN COUNT(o) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Order o JOIN o.orderDetails od " +
            "WHERE o.customer.id = :customerId AND od.product.id = :productId")
    boolean existsByCustomerIdAndProductId(@Param("customerId") Integer customerId, @Param("productId") Integer productId);
}
