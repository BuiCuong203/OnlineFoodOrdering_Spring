package com.FoodOrder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.FoodOrder.model.Order;
import com.FoodOrder.model.User;
import com.FoodOrder.service.OrderService;
import com.FoodOrder.service.UserService;

@RestController
@RequestMapping("/api/admin")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @GetMapping("/order/restaurant/{id}")
    public ResponseEntity<List<Order>> getOrderHistory(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id,
            @RequestParam(required = false) String orderStatus)
            throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Order> orders = orderService.getOrderRestaurant(id, orderStatus);

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PutMapping("/order/{orderId}/{orderStatus}")
    public ResponseEntity<Order> updateOrderStatus(
            @RequestHeader("Authorization") String jwt, @PathVariable Long orderId, @PathVariable String orderStatus)
            throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Order order = orderService.updateOrder(orderId, orderStatus);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
