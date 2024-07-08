package com.skyitschool.skyitschoolecom.services.service;

import com.skyitschool.skyitschoolecom.dto.CustomerRequestBody;
import com.skyitschool.skyitschoolecom.dto.CustomerResponseBody;
import com.skyitschool.skyitschoolecom.entity.AuthenticationType;
import com.skyitschool.skyitschoolecom.entity.Country;
import com.skyitschool.skyitschoolecom.entity.Customer;
import com.skyitschool.skyitschoolecom.exception.CustomerNotFoundException;
import com.skyitschool.skyitschoolecom.loginDTO.LoginRequest;
import com.skyitschool.skyitschoolecom.loginDTO.LoginResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CustomerService {

   // LoginResponse login(LoginRequest loginRequest);
    List<CustomerResponseBody> getAllCustomers();
    List<CustomerResponseBody> customerListByPage(int pageNum, int pageSize);
    boolean isEmailUnique(String email);
    void updateCustomer(CustomerRequestBody customerRequestBody, Integer id) throws CustomerNotFoundException;
    CustomerResponseBody registerCustomer(CustomerRequestBody customerRequestBody);
    Customer getCustomerByEmail(String email);
    boolean verify(String verificationCode);
    void updateCustomerEnabledStatus(Integer id, boolean enabled);
    Customer getCustomerById(Integer id) throws CustomerNotFoundException;
    List<Country> listAllCountries();
    void updateAuthenticationType(Customer customer, AuthenticationType type);
    void delete(Integer id) throws CustomerNotFoundException;
}
