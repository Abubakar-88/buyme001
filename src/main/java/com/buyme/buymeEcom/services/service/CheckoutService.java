package com.buyme.buymeEcom.services.service;

import com.buyme.buymeEcom.orderDTO.CheckoutRequest;
import com.buyme.buymeEcom.orderDTO.CheckoutResponse;


public interface CheckoutService {
    CheckoutResponse checkout(CheckoutRequest checkoutRequest);
}