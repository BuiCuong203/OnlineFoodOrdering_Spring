package com.FoodOrder.service;

import java.io.UnsupportedEncodingException;

import jakarta.servlet.http.HttpServletRequest;

import com.FoodOrder.response.PaymentResponse;

public interface VNPayService {
    public PaymentResponse createVNPayPayment(HttpServletRequest request, Long amount)
            throws UnsupportedEncodingException;
}
