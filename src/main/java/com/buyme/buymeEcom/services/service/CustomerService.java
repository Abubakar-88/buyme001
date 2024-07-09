package com.buyme.buymeEcom.services.service;

import com.buyme.buymeEcom.dto.CustomerRequestBody;
import com.buyme.buymeEcom.dto.CustomerResponseBody;
import com.buyme.buymeEcom.entity.AuthenticationType;
import com.buyme.buymeEcom.entity.Country;
import com.buyme.buymeEcom.entity.Customer;
import com.buyme.buymeEcom.exception.CustomerNotFoundException;

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
