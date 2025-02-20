package com.FoodOrder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.FoodOrder.model.Food;
import com.FoodOrder.model.User;
import com.FoodOrder.service.FoodService;
import com.FoodOrder.service.RestaurantService;
import com.FoodOrder.service.UserService;

@RestController
@RequestMapping("/api/foods")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> getRestaurantFood(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long restaurantId,
            @RequestParam boolean isVegetarian,
            @RequestParam boolean isNonveg,
            @RequestParam boolean isSeasonal,
            @RequestParam(required = false) String category)
            throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Food> foods = foodService.getRestaurantsFood(restaurantId, isVegetarian, isNonveg, isSeasonal, category);

        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(
            @RequestHeader("Authorization") String jwt, @RequestParam String keyword) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Food> foods = foodService.searchFood(keyword);

        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    @GetMapping("{foodId}")
    public ResponseEntity<Food> findFoodById(@RequestHeader("Authorization") String jwt, @PathVariable Long foodId)
            throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Food food = foodService.findFoodById(foodId);

        return new ResponseEntity<>(food, HttpStatus.OK);
    }
}
