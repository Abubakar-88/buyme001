package com.skyitschool.skyitschoolecom.services.service;

import com.skyitschool.skyitschoolecom.orderDTO.CheckoutRequest;
import com.skyitschool.skyitschoolecom.orderDTO.CheckoutResponse;


public interface CheckoutService {
    CheckoutResponse checkout(CheckoutRequest checkoutRequest);
}