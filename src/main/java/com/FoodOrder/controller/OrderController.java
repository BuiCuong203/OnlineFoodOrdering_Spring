package com.FoodOrder.controller;

import com.FoodOrder.model.CartItem;
import com.FoodOrder.model.Order;
import com.FoodOrder.model.User;
import com.FoodOrder.request.OrderRequest;
import com.FoodOrder.service.OrderService;
import com.FoodOrder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping("/order")
    public ResponseEntity<Order> createOrder(
            @RequestHeader("Authorization") String jwt,
            @RequestBody OrderRequest request
            ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Order order = orderService.createOrder(request, user);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/order/user")
    public ResponseEntity<List<Order>> getOrderHistory(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Order> orders = orderService.getOrderUser(user.getId());

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
