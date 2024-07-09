package com.buyme.buymeEcom.services.Impl;

import com.buyme.buymeEcom.entity.AuthenticationType;
import com.buyme.buymeEcom.entity.Country;
import com.buyme.buymeEcom.entity.Customer;
import com.buyme.buymeEcom.entity.State;
import com.skyitschool.skyitschoolecom.entity.*;
import com.buyme.buymeEcom.dto.CustomerRequestBody;
import com.buyme.buymeEcom.dto.CustomerResponseBody;
import com.buyme.buymeEcom.exception.CustomerNotFoundException;
import com.buyme.buymeEcom.repository.CountryRepository;
import com.buyme.buymeEcom.repository.CustomerRepository;
import com.buyme.buymeEcom.services.service.CustomerService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepo;



    @Autowired private PasswordEncoder passwordEncoder;

    @Autowired private CountryRepository countryRepo;
    private static final String ALPHANUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";



    @Override
    public List<CustomerResponseBody> getAllCustomers() {
        List<Customer> customers = customerRepo.findAll();
       // List<CustomerResponseBody> customerResponseBodies = new ArrayList<>();
        return customers.stream().map(this::convertToCustomerResponseBody).collect(Collectors.toList());

     //   return customerRepo.findAll();
    }

    @Override
    public List<CustomerResponseBody> customerListByPage(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by("name").ascending());
      //  return customerRepo.findAll(pageable).getContent();
        List<Customer> customers = customerRepo.findAll(pageable).getContent();
        return customers.stream().map(this::convertToCustomerResponseBody).collect(Collectors.toList());
    }


    private CustomerResponseBody convertToCustomerResponseBody(Customer customer) {
        CustomerResponseBody response = new CustomerResponseBody();
        response.setId(customer.getId());
        response.setFirstName(customer.getFirstName());
        response.setLastName(customer.getLastName());
        response.setEmail(customer.getEmail());
        response.setPhoneNumber(customer.getPhoneNumber());
        response.setAddressLine1(customer.getAddressLine1());
        response.setCity(customer.getCity());
        response.setState(customer.getState());
        response.setPostalCode(customer.getPostalCode());
        response.setEnabled(customer.isEnabled());
        response.setFullName(customer.getFullName());
        response.setVerificationCode(customer.getVerificationCode());
        response.setCreatedTime(customer.getCreatedTime());
        response.setAuthenticationType(customer.getAuthenticationType().toString());

        if (customer.getCountry() != null) {
            response.setCountryId(customer.getCountry().getId());
        }
        response.setState(customer.getState());

        return response;
    }
    @Override
    public boolean isEmailUnique(String email) {
        Customer customer = customerRepo.findByEmail(email);
        return customer == null;
    }

    @Override
    public CustomerResponseBody registerCustomer(CustomerRequestBody customerRequestBody) {

        if (!isEmailUnique(customerRequestBody.getEmail())) {
            throw new RuntimeException("Email already exists");
        }


        Customer customer = new Customer();
        customer.setEmail(customerRequestBody.getEmail());
        customer.setPassword(customerRequestBody.getPassword()); // Set raw password
        encodePassword(customer); // Encode and set the password
        customer.setEnabled(false);
        customer.setCreatedTime(new Date());
        customer.setAuthenticationType(AuthenticationType.DATABASE);
        customer.setFirstName(customerRequestBody.getFirstName());
        customer.setLastName(customerRequestBody.getLastName());
        customer.setPhoneNumber(customerRequestBody.getPhoneNumber());
        customer.setAddressLine1(customerRequestBody.getAddressLine1());
        customer.setCity(customerRequestBody.getCity());
       // customer.setState(customerRequestBody.getState());
        customer.setPostalCode(customerRequestBody.getPostalCode());

        String randomCode = RandomString.make(64);
        customer.setVerificationCode(randomCode);

        if (customerRequestBody.getCountry() == null || customerRequestBody.getCountry().getId() == null) {
            throw new RuntimeException("Country ID cannot be null");
        }

        Optional<Country> selectedCountryOpt = countryRepo.findById(customerRequestBody.getCountry().getId());
        if (!selectedCountryOpt.isPresent()) {
            throw new RuntimeException("Country not found");
        }

        Country selectedCountry = selectedCountryOpt.get();
        customer.setCountry(selectedCountry);

        // Ensure the state is within the selected country
        if (customerRequestBody.getCountry().getState() == null || customerRequestBody.getCountry().getState().getName() == null) {
            throw new RuntimeException("State name cannot be null");
        }


        Optional<State> selectedStateOpt = selectedCountry.getStates().stream()
                .filter(state -> state.getName().equalsIgnoreCase(customerRequestBody.getCountry().getState().getName()))
                .findFirst();


        if (!selectedStateOpt.isPresent()) {
            throw new RuntimeException("State not found in the specified country");
        }

        State selectedState = selectedStateOpt.get();
        customer.setState(selectedState.getName());

        customerRepo.save(customer);
        // Create response DTO

      return convertToCustomerResponseBody(customer);

//        encodePassword(customer);
//        customer.setEnabled(false);
//        customer.setCreatedTime(new Date());
//        customer.setAuthenticationType(AuthenticationType.DATABASE);
//
//        String randomCode =RandomString.make(64);
//        customer.setVerificationCode(randomCode);
//
//        customer.setCountry(selectedCountry); // Associate the selected country with the customer
//
//        customerRepo.save(customer);
    }

    @Override
    public void updateCustomer(CustomerRequestBody customerRequestBody, Integer id) throws CustomerNotFoundException {
        Optional<Customer> customerOpt = customerRepo.findById(id);
        if (!customerOpt.isPresent()) {
            throw new CustomerNotFoundException("Customer not found");
        }

        Customer customer = customerOpt.get();
        customer.setEmail(customerRequestBody.getEmail());

        if (customerRequestBody.getPassword() != null && !customerRequestBody.getPassword().isEmpty()) {
            customer.setPassword(customerRequestBody.getPassword()); // Set raw password
            encodePassword(customer); // Encode and set the password
        }

        customer.setFirstName(customerRequestBody.getFirstName());
        customer.setLastName(customerRequestBody.getLastName());
        customer.setPhoneNumber(customerRequestBody.getPhoneNumber());
        customer.setAddressLine1(customerRequestBody.getAddressLine1());
        customer.setCity(customerRequestBody.getCity());
        customer.setState(customerRequestBody.getState());
        customer.setPostalCode(customerRequestBody.getPostalCode());

        if (customerRequestBody.getCountry() == null || customerRequestBody.getCountry().getId() == null) {
            throw new RuntimeException("Country ID cannot be null");
        }

        Optional<Country> selectedCountryOpt = countryRepo.findById(customerRequestBody.getCountry().getId());
        if (!selectedCountryOpt.isPresent()) {
            throw new RuntimeException("Country not found");
        }

        Country selectedCountry = selectedCountryOpt.get();
        customer.setCountry(selectedCountry);

        State selectedState = selectedCountry.getStates().stream()
                .filter(state -> state.getName().equalsIgnoreCase(customerRequestBody.getState()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("State not found in the specified country"));

        customer.setState(selectedState.getName());

        customerRepo.save(customer);
    }
    private void encodePassword(Customer customer) {
        String encodedPassword = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(encodedPassword);
    }
    @Override
    public Customer getCustomerByEmail(String email) {
        return customerRepo.findByEmail(email);
    }
    @Override
    public boolean verify(String verificationCode) {
        Customer customer = customerRepo.findByVerificationCode(verificationCode);

        if (customer == null || customer.isEnabled()) {
            return false;
        } else {
            customer.setEnabled(true);
            customerRepo.save(customer);
            return true;
        }
    }
    @Override
    public void updateCustomerEnabledStatus(Integer id, boolean enabled) {
        customerRepo.updateEnabledStatus(id, enabled);
    }

    @Override
    public Customer getCustomerById(Integer id) throws CustomerNotFoundException {
        try {
            return customerRepo.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new CustomerNotFoundException("Could not find any customers with ID " + id);
        }
    }

    @Override
    public List<Country> listAllCountries() {
        return countryRepo.findAllByOrderByNameAsc();
    }

    @Override
    public void updateAuthenticationType(Customer customer, AuthenticationType type) {
        if (!customer.getAuthenticationType().equals(type)) {
            customer.setAuthenticationType(type);
            customerRepo.save(customer);
        }
    }

    @Override
    public void delete(Integer id) throws CustomerNotFoundException {
        Long count = customerRepo.countById(id);
        if (count == null || count == 0) {
            throw new CustomerNotFoundException("Could not find any customers with ID " + id);
        }

        customerRepo.deleteById(id);
    }







}
