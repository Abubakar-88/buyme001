package com.skyitschool.skyitschoolecom.repository;

import com.skyitschool.skyitschoolecom.entity.order.OrderTrack;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderTrackRepository extends JpaRepository<OrderTrack, Long> {
}
