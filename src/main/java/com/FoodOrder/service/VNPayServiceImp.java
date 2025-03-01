package com.FoodOrder.service;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FoodOrder.response.PaymentResponse;
import com.FoodOrder.util.VNPayUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VNPayServiceImp implements VNPayService {

    @Autowired
    private VNPayUtil vnPayUtil;

    @Override
    public PaymentResponse createVNPayPayment(HttpServletRequest request, Long amout)
            throws UnsupportedEncodingException {
        Long amount = amout * 100L;
        String IpAddr = vnPayUtil.getIpAddress(request);
        Map<String, String> vnp_Params = vnPayUtil.getVNPayConfig();
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_IpAddr", IpAddr);

        String queryUrl = vnPayUtil.hashAllFields(vnp_Params, true);
        String hashData = vnPayUtil.hashAllFields(vnp_Params, false);

        queryUrl += "&vnp_SecureHash=" + hashData;
        String paymentUrl = vnPayUtil.getVnp_PayUrl() + "?" + queryUrl;

        return PaymentResponse.builder()
                .code("OK")
                .message("Success")
                .paymentUrl(paymentUrl)
                .build();
    }
}
