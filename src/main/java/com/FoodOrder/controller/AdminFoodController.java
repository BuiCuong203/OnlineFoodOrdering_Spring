package com.FoodOrder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.FoodOrder.model.Food;
import com.FoodOrder.model.Restaurant;
import com.FoodOrder.model.User;
import com.FoodOrder.request.CreateFoodRequest;
import com.FoodOrder.response.MessageResponse;
import com.FoodOrder.service.FoodService;
import com.FoodOrder.service.RestaurantService;
import com.FoodOrder.service.UserService;

@RestController
@RequestMapping("/api/admin/foods")
public class AdminFoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping()
    public ResponseEntity<Food> createFood(
            @RequestHeader("Authorization") String jwt, @RequestBody CreateFoodRequest request) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.findRestaurantById(request.getRestaurantId());
        Food food = foodService.createFood(request, request.getCategory(), restaurant);

        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    @DeleteMapping("/{foodId}")
    public ResponseEntity<MessageResponse> deleteFood(
            @RequestHeader("Authorization") String jwt, @PathVariable Long foodId) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        foodService.deleteFood(foodId);
        MessageResponse response =
                MessageResponse.builder().message("Food deleted successfully").build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{foodId}/status")
    public ResponseEntity<Food> updateAvailibityStatus(
            @RequestHeader("Authorization") String jwt, @PathVariable Long foodId) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Food food = foodService.updateAvailibityStatus(foodId);

        return new ResponseEntity<>(food, HttpStatus.OK);
    }
}
