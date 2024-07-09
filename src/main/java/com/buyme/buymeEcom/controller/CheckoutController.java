package com.buyme.buymeEcom.controller;

import com.buyme.buymeEcom.orderDTO.CheckoutRequest;
import com.buyme.buymeEcom.orderDTO.CheckoutResponse;
import com.buyme.buymeEcom.services.service.CheckoutService;
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
