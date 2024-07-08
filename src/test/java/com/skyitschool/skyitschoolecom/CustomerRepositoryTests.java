package com.skyitschool.skyitschoolecom;



import com.skyitschool.skyitschoolecom.entity.Country;
import com.skyitschool.skyitschoolecom.entity.Customer;
import com.skyitschool.skyitschoolecom.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class CustomerRepositoryTests {

    @Autowired
  private CustomerRepository repo;
   @Autowired private TestEntityManager entityManager;

    @Test
    public void testCreateCustomer1() {
        Integer countryId = 1; // BD
        Country country = entityManager.find(Country.class, countryId);

        Customer customer = new Customer();
        customer.setCountry(country);
        customer.setFirstName("David");
        customer.setLastName("Fountaine");
        customer.setPassword("password123");
        customer.setEmail("david.s.fountaine@gmail.com");
        customer.setPhoneNumber("312-462-7518");
        customer.setAddressLine1("1927  West Dhaka");
        customer.setCity("Dhaka");
        customer.setState("Dhaka");
        customer.setPostalCode("1213");
        customer.setCreatedTime(new Date());

        Customer savedCustomer = repo.save(customer);

        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getId()).isGreaterThan(0);
    }

//    private Guards assertThat(Customer savedCustomer) {
//    }

    @Test
    public void testCreateCustomer2() {
        Integer countryId = 1; // BD
     //   Country country = entityManager.find(Country.class, countryId);

        Customer customer = new Customer();
     //   customer.setCountry(country);
        customer.setFirstName("David2");
        customer.setLastName("Fountaine2");
        customer.setPassword("password123");
        customer.setEmail("david.s.fountaine2@gmail.com");
        customer.setPhoneNumber("312-462-7518");
        customer.setAddressLine1("1927  West Dhaka2");
        customer.setCity("Dhaka2");
        customer.setState("Dhaka2");
        customer.setPostalCode("1213");
        customer.setCreatedTime(new Date());

//        Customer savedCustomer = repo.save(customer);
//
//        assertThat(savedCustomer).isNotNull();
//        assertThat(savedCustomer.getId()).isGreaterThan(0);
    }


    @Test
    public void testListCustomers(){
//        Iterable<Customer> customers = repo.findAll();
//        customers.forEach(System.out::println);
//        assertThat(customers).hasSizeGreaterThan(1);
    }
    @Test
    public void testUpdateCustomer() {
        Integer customerId = 1;
        String lastName = "Stanfield";
//
//        Customer customer = repo.findById(customerId).get();
//        customer.setLastName(lastName);
//        customer.setEnabled(true);
//
//        Customer updatedCustomer = repo.save(customer);
//        assertThat(updatedCustomer.getLastName()).isEqualTo(lastName);
    }

    @Test
    public void testGetCustomer() {
//        Integer customerId = 3;
//        Optional<Customer> findById = repo.findById(customerId);
//
//        assertThat(findById).isPresent();
//
//        Customer customer = findById.get();
//        System.out.println(customer);
//    }
//    @Test
//    public void testFindByEmail() {
//        String email = "david.s.fountaine@gmail.com";
//        Customer customer = repo.findByEmail(email);
//
//        assertThat(customer).isNotNull();
//        System.out.println(customer);
  }
}
