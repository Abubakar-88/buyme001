package com.buyme.buymeEcom.controller;

import com.buyme.buymeEcom.entity.Country;
import com.buyme.buymeEcom.services.service.CountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryRestController {

    private final CountryService countryService;

    public CountryRestController(CountryService countryService) {
        this.countryService = countryService;
    }
    @GetMapping("/list")
    public List<Country> listAll() {
        return countryService.getAllCountries();
    }

//    @GetMapping("/list")
//    public ResponseEntity<Page<Country>> listAll(@RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "10") int pageSize) {
//        Page<Country> countries = countryService.getAllCountries(pageNo, pageSize);
//        return ResponseEntity.ok(countries);
//    }

    @PostMapping("/save")
    public ResponseEntity<Country> save(@RequestBody Country country) {
        Country savedCountry = countryService.saveCountry(country);
        return ResponseEntity.ok(savedCountry);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Integer id, @RequestBody Country country) {
        boolean isUpdated = countryService.updateCountry(id, country);
        if (isUpdated) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        boolean isDeleted = countryService.deleteCountryById(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/withState/{id}")
    public Country getCountry(@PathVariable Integer id) {
        return countryService.getCountryWithStates(id);
    }

}
