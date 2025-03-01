package com.FoodOrder.controller;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.FoodOrder.model.Cart;
import com.FoodOrder.model.Order;
import com.FoodOrder.model.User;
import com.FoodOrder.request.OrderRequest;
import com.FoodOrder.response.PaymentResponse;
import com.FoodOrder.service.CartService;
import com.FoodOrder.service.OrderService;
import com.FoodOrder.service.UserService;
import com.FoodOrder.service.VNPayService;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private VNPayService vnPayService;

    @PostMapping("/order")
    public ResponseEntity<?> createOrder(
            HttpServletRequest req, @RequestHeader("Authorization") String jwt, @RequestBody OrderRequest request)
            throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        if (request.getMethodPayment().equals("CASH")) {
            Order order = orderService.createOrder(request, user);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else if (request.getMethodPayment().equals("VNPAY")) {
            Cart cart = cartService.findCartByUserId(user.getId());
            Long amout = cart.getTotal();

            Order order = orderService.createOrder(request, user);
            PaymentResponse paymentResponse = vnPayService.createVNPayPayment(req, amout);
            return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
        }
        return null;
    }

    @GetMapping("/order/user")
    public ResponseEntity<List<Order>> getOrderHistory(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Order> orders = orderService.getOrderUser(user.getId());

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
