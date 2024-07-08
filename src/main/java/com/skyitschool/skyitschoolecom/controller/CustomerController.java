package com.skyitschool.skyitschoolecom.controller;


import com.skyitschool.skyitschoolecom.dto.CustomerRequestBody;
import com.skyitschool.skyitschoolecom.dto.CustomerResponseBody;
import com.skyitschool.skyitschoolecom.entity.AuthenticationType;
import com.skyitschool.skyitschoolecom.entity.Customer;
import com.skyitschool.skyitschoolecom.exception.CustomerNotFoundException;
import com.skyitschool.skyitschoolecom.loginDTO.LoginRequest;
import com.skyitschool.skyitschoolecom.loginDTO.LoginResponse;
import com.skyitschool.skyitschoolecom.services.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired private CustomerService customerService;

    @GetMapping()
    public List<CustomerResponseBody> listCustomers(){
        return customerService.getAllCustomers();
    }

    @GetMapping("/page/{pageNum}")
    public ResponseEntity<List<CustomerResponseBody>> listCustomerByPage(@PathVariable(name = "pageNum") int pageNum,
                                      @RequestParam(defaultValue = "10") int pageSize){
        List<CustomerResponseBody> customerList = customerService.customerListByPage(pageNum, pageSize);
        return ResponseEntity.ok(customerList);
    }

//    @PostMapping("/login")
//    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
//        LoginResponse loginResponse = customerService.login(loginRequest);
//        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
//    }

    @PostMapping("/register")
    public ResponseEntity<CustomerResponseBody> saveCustomer(@RequestBody CustomerRequestBody customerRequestBody) {
        CustomerResponseBody  response = customerService.registerCustomer(customerRequestBody);
        if (response.getError() != null) {
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
       // return new ResponseEntity<>("Customer saved successfully", HttpStatus.CREATED);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCustomer(@RequestBody CustomerRequestBody customerRequestBody, @PathVariable Integer id) throws CustomerNotFoundException {
        customerService.updateCustomer(customerRequestBody, id);

        return new ResponseEntity<>("Customer Update successfully", HttpStatus.CREATED);
    }
    @PutMapping("/{id}/enabled/{enabled}")
    public ResponseEntity<String> updateCustomerEnabledStatus(
            @PathVariable("id") Integer id,
            @PathVariable("enabled") boolean enabled) {

        customerService.updateCustomerEnabledStatus(id, enabled);

        String status = enabled ? "enabled" : "disabled";
        return new ResponseEntity<>("Customer with ID " + id + " has been " + status, HttpStatus.OK);
    }


//    For path variable: /api/customers/abcd1234/verify
//    For query parameter: /api/customers/verify?verificationCode=abcd1234
    @GetMapping("/verify")
    public ResponseEntity<String> verifyCustomer(@RequestParam("verificationCode") String verificationCode) {
        boolean verified = customerService.verify(verificationCode);

        if (verified) {
            return new ResponseEntity<>("Customer verified successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid or expired verification code", HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/{customerId}/authentication")
    public ResponseEntity<String> updateAuthenticationType(
            @PathVariable("customerId") Integer customerId,
            @RequestParam("type") AuthenticationType type) throws CustomerNotFoundException {
 
        Customer customer = customerService.getCustomerById(customerId);
        if (customer == null) {
            return new ResponseEntity<>("Customer with ID " + customerId + " not found", HttpStatus.NOT_FOUND);
        }

        customerService.updateAuthenticationType(customer, type);

        return new ResponseEntity<>("Authentication type updated successfully for customer with ID " + customerId, HttpStatus.OK);
    }

}
