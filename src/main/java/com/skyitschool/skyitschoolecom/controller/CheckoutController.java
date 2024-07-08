package com.skyitschool.skyitschoolecom.controller;

import com.skyitschool.skyitschoolecom.orderDTO.CheckoutRequest;
import com.skyitschool.skyitschoolecom.orderDTO.CheckoutResponse;
import com.skyitschool.skyitschoolecom.services.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/checkout")
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;

    @PostMapping
    public ResponseEntity<CheckoutResponse> checkout(@RequestBody CheckoutRequest checkoutRequest) {
        CheckoutResponse checkoutResponse = checkoutService.checkout(checkoutRequest);
        return new ResponseEntity<>(checkoutResponse, HttpStatus.OK);
    }
}
