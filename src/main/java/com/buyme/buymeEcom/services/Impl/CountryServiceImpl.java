package com.buyme.buymeEcom.services.Impl;

import com.buyme.buymeEcom.entity.Country;
import com.buyme.buymeEcom.repository.CountryRepository;
import com.buyme.buymeEcom.services.service.CountryService;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository repo;

    public CountryServiceImpl(CountryRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Country> getAllCountries() {
        return repo.findAllByOrderByNameAsc();
    }
    //    public Page<Country> getAllCountries(int pageNo, int pageSize) {
//        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("name").ascending());
//        return repo.findAll(pageable);
//    }
    @Override
    public Country saveCountry(Country country) {
        return repo.save(country);
    }

    @Override
    public boolean deleteCountryById(Integer id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Country> getCountryById(Integer id) {
        return repo.findById(id);
    }

    @Override
    public boolean updateCountry(Integer id, Country country) {
        Optional<Country> existingCountry = repo.findById(id);
        if (existingCountry.isPresent()) {
            country.setId(id);
            repo.save(country);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Country getCountryWithStates(Integer id) {
        Country country = repo.findByIdWithStates(id);
        // Initialize the states collection
        Hibernate.initialize(country.getStates());
        System.out.println("Number of states: " + country.getStates().size());
        return country;
    }




}
