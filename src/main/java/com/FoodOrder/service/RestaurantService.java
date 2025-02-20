package com.FoodOrder.service;

import java.util.List;

import com.FoodOrder.dto.RestaurantDto;
import com.FoodOrder.model.Restaurant;
import com.FoodOrder.model.User;
import com.FoodOrder.request.CreateRestaurantRequest;

public interface RestaurantService {
    public Restaurant createRestaurant(CreateRestaurantRequest request, User user) throws Exception;

    public Restaurant updateRestuarant(Long restaurantId, CreateRestaurantRequest updateRestaurant) throws Exception;

    public void deleteRestaurant(Long restaurantId) throws Exception;

    public List<Restaurant> getAllRestaurant() throws Exception;

    public List<Restaurant> searchRestaurant(String keyword) throws Exception;

    public Restaurant findRestaurantById(Long id) throws Exception;

    public Restaurant getRestaurantByUserId(Long userId) throws Exception;

    public RestaurantDto addToFavourites(Long restaurnatId, User user) throws Exception;

    public Restaurant updateRestaurantStatus(Long id) throws Exception;
}
