package com.FoodOrder.service;

import com.FoodOrder.response.PaymentResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;

public interface VNPayService {
    public PaymentResponse createVNPayPayment(HttpServletRequest request) throws UnsupportedEncodingException;
}
