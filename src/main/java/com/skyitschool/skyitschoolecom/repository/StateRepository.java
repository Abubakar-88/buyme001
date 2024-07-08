package com.skyitschool.skyitschoolecom.repository;

import com.skyitschool.skyitschoolecom.entity.Country;
import com.skyitschool.skyitschoolecom.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
@EnableJpaRepositories
public interface StateRepository extends JpaRepository<State, Integer> {
    public List<State> findByCountryOrderByNameAsc(Country country);
}
