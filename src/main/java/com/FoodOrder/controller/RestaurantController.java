package com.FoodOrder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.FoodOrder.dto.RestaurantDto;
import com.FoodOrder.model.Restaurant;
import com.FoodOrder.model.User;
import com.FoodOrder.service.RestaurantService;
import com.FoodOrder.service.UserService;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurant(
            @RequestHeader("Authorization") String jwt, @RequestParam String keyword) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Restaurant> restaurants = restaurantService.searchRestaurant(keyword);

        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Restaurant>> getAllRestaurant(@RequestHeader("Authorization") String jwt)
            throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Restaurant> restaurants = restaurantService.getAllRestaurant();

        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @GetMapping("/{retaurantId}")
    public ResponseEntity<Restaurant> findRestaurantById(
            @RequestHeader("Authorization") String jwt, @PathVariable Long retaurantId) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.findRestaurantById(retaurantId);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @PostMapping("/{id}/add-favourites")
    public ResponseEntity<RestaurantDto> addFavourites(
            @RequestHeader("Authorization") String jwt, @PathVariable Long id) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        RestaurantDto restaurantDto = restaurantService.addToFavourites(id, user);

        return new ResponseEntity<>(restaurantDto, HttpStatus.OK);
    }
}
