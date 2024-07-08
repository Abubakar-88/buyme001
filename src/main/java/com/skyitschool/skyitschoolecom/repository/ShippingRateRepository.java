package com.skyitschool.skyitschoolecom.repository;

import com.skyitschool.skyitschoolecom.entity.Country;
import com.skyitschool.skyitschoolecom.entity.ShippingRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShippingRateRepository extends JpaRepository<ShippingRate, Long> {
    Optional<ShippingRate> findByCountryAndState(Country country, String state);
}
