package com.skyitschool.skyitschoolecom.services.service;

import com.skyitschool.skyitschoolecom.entity.Country;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface CountryService {
    List<Country> getAllCountries();
    Country saveCountry(Country country);
    boolean deleteCountryById(Integer id);
    Optional<Country> getCountryById(Integer id);
    boolean updateCountry(Integer id, Country country);
    Country getCountryWithStates(Integer id);

}
