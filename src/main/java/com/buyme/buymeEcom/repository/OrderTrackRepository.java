package com.buyme.buymeEcom.repository;

import com.buyme.buymeEcom.entity.order.OrderTrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderTrackRepository extends JpaRepository<OrderTrack, Long> {
}
