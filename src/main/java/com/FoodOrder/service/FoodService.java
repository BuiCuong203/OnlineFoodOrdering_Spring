package com.FoodOrder.service;

import java.util.List;

import com.FoodOrder.model.Category;
import com.FoodOrder.model.Food;
import com.FoodOrder.model.Restaurant;
import com.FoodOrder.request.CreateFoodRequest;

public interface FoodService {
    public Food createFood(CreateFoodRequest request, Category category, Restaurant restaurant);

    void deleteFood(Long foodId) throws Exception;

    public List<Food> getRestaurantsFood(
            Long restaurantId, boolean isVegetarian, boolean isNonveg, boolean isSeasonal, String category);

    public List<Food> searchFood(String keyword);

    public Food findFoodById(Long foodId) throws Exception;

    public Food updateAvailibityStatus(Long foodId) throws Exception;
}
