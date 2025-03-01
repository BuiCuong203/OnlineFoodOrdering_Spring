package com.FoodOrder.controller;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.FoodOrder.response.PaymentResponse;
import com.FoodOrder.service.OrderService;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/vn-pay-callback")
    public ResponseEntity<PaymentResponse> payCallbackHandler(HttpServletRequest request) {
        String status = request.getParameter("vnp_ResponseCode");
        if (status.equals("00")) {
            PaymentResponse paymentResponse =
                    PaymentResponse.builder().code(status).message("Success").build();
            return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
        } else {
            PaymentResponse paymentResponse =
                    PaymentResponse.builder().code(status).message("Fail").build();
            return new ResponseEntity<>(paymentResponse, HttpStatus.BAD_GATEWAY);
        }
    }
}
