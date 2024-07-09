package com.buyme.buymeEcom;

import com.buyme.buymeEcom.entity.Country;
import com.buyme.buymeEcom.entity.State;
import com.buyme.buymeEcom.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Rollback(false)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class CountryRepositoryTest {
//    @Autowired
//    private MockMvc mockMvc;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CountryRepository repo;

//    @Test
//    public void testCreateCountry() {
//        Country country = repo.save(new Country("TEST", "TT"));
//        assertThat(country).isNotNull();
//        assertThat(country.getId()).isGreaterThan(0);
//    }

    @Test
    public void testListCountries() {
        List<Country> listCountries = repo.findAllByOrderByNameAsc();
        listCountries.forEach(System.out::println);

        assertThat(listCountries.size()).isGreaterThan(0);
    }

//    @Test
//    public void testUpdateCountry() {
//        Integer id = 2;
//        String name = "Republic of India";
//
//        Country country = repo.findById(id).get();
//        country.setName(name);
//
//        Country updatedCountry = repo.save(country);
//
//        assertThat(updatedCountry.getName()).isEqualTo(name);
//    }

    @Test
    public void testGetCountry() {
        Integer countryId = 1;
        Country country = repo.findByIdWithStates(countryId);
        System.out.println("Country: " + country.getName());
        System.out.println("Number of states: " + country.getStates().size());
        for (State state : country.getStates()) {
            System.out.println("State: " + state.getName());
        }
    }
//    @Test
//    public void testDeleteCountry() {
//        Integer id = 5;
//        repo.deleteById(id);
//
//        Optional<Country> findById = repo.findById(id);
//        assertThat(findById.isEmpty());
//    }
}
