package com.skyitschool.skyitschoolecom.repository;

import com.skyitschool.skyitschoolecom.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
