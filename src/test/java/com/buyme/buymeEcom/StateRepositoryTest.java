//package com.skyitschool.skyitschoolecom;
//
//import com.skyitschool.skyitschoolecom.entity.Country;
//import com.skyitschool.skyitschoolecom.entity.State;
//import com.skyitschool.skyitschoolecom.repository.StateRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import static org.assertj.core.api.Assertions.assertThat;
//import java.util.List;
//import java.util.Optional;
//
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//public class StateRepositoryTest {
//
//    @Autowired
//    private StateRepository repo;
//
//
//
//    @Autowired
//    private TestEntityManager entityManager;
//
//    @Test
//    public void testCreateStatesInBangladesh() {
//        Integer countryId = 1;
//        Country country = entityManager.find(Country.class, countryId);
//
////		State state = repo.save(new State("Dhaka", country));
////		State state = repo.save(new State("Narsingdi", country));
//		State state = repo.save(new State("Narayangonj", country));
////        State state = repo.save(new State("West Bengal", country));
//
//        assertThat(state).isNotNull();
//        assertThat(state.getId()).isGreaterThan(0);
//    }
//
//
//
//    @Test
//    public void testCreateStatesInIndia() {
//        Integer countryId = 2;
//        Country country = entityManager.find(Country.class, countryId);
//
////		State state = repo.save(new State("Karnataka", country));
//		State state = repo.save(new State("Test", country));
////	State state = repo.save(new State("Uttar Pradesh", country));
//  //      State state = repo.save(new State("West Bengal", country));
//
//        assertThat(state).isNotNull();
//        assertThat(state.getId()).isGreaterThan(0);
//    }
//
//    @Test
//    public void testListStatesByCountry() {
//        Integer countryId = 2;
//        Country country = entityManager.find(Country.class, countryId);
//        List<State> listStates = repo.findByCountryOrderByNameAsc(country);
//
//        listStates.forEach(System.out::println);
//
//        assertThat(listStates.size()).isGreaterThan(0);
//    }
//
//    @Test
//    public void testUpdateState() {
//        Integer stateId = 4;
//        String stateName = "Tamil Nadu";
//        State state = repo.findById(stateId).get();
//
//        state.setName(stateName);
//        State updatedState = repo.save(state);
//
//        assertThat(updatedState.getName()).isEqualTo(stateName);
//    }
//    @Test
//    public void testGetState() {
//        Integer stateId = 1;
//        Optional<State> findById = repo.findById(stateId);
//        assertThat(findById.isPresent());
//    }
//    @Test
//    public void testDeleteState() {
//        Integer stateId = 7;
//        repo.deleteById(stateId);
//
//        Optional<State> findById = repo.findById(stateId);
//        assertThat(findById.isEmpty());
//    }
//
//}
