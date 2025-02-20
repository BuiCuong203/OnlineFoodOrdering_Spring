package com.FoodOrder.service;

import java.util.List;

import com.FoodOrder.model.Order;
import com.FoodOrder.model.User;
import com.FoodOrder.request.OrderRequest;

public interface OrderService {
    public Order createOrder(OrderRequest request, User user) throws Exception;

    public Order updateOrder(Long orderId, String orderStatus) throws Exception;

    public void cancelOrder(Long orderId) throws Exception;

    public List<Order> getOrderUser(Long userId) throws Exception;

    public List<Order> getOrderRestaurant(Long restaurantId, String orderStatus) throws Exception;

    public Order findOrderById(Long orderId) throws Exception;
}
