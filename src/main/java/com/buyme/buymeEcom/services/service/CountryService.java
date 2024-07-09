package com.buyme.buymeEcom.services.service;

import com.buyme.buymeEcom.entity.Country;

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
