package com.FoodOrder.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FoodOrder.model.Category;
import com.FoodOrder.model.Food;
import com.FoodOrder.model.Restaurant;
import com.FoodOrder.repository.FoodRepository;
import com.FoodOrder.request.CreateFoodRequest;

@Service
public class FoodServiceImp implements FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Override
    public Food createFood(CreateFoodRequest request, Category category, Restaurant restaurant) {
        Food food = Food.builder()
                .category(category)
                .restaurant(restaurant)
                .description(request.getDescription())
                .images(request.getImages())
                .name(request.getName())
                .price(request.getPrice())
                .ingredientsItems(request.getIngredientsItems())
                .isSeasonal(request.isSeasional())
                .isVegetarian(request.isVegetarian())
                .creationDate(new Date())
                .build();
        Food saveFood = foodRepository.save(food);

        return saveFood;
    }

    @Override
    public void deleteFood(Long foodId) throws Exception {
        Food food = findFoodById(foodId);

        foodRepository.delete(food);
    }

    @Override
    public List<Food> getRestaurantsFood(
            Long restaurantId, boolean isVegetarian, boolean isNonveg, boolean isSeasonal, String category) {
        List<Food> foods = foodRepository.findByRestaurantId(restaurantId);

        if (isVegetarian) {
            foods = filterByVegetarian(foods, isVegetarian);
        }
        if (isNonveg) {
            foods = filterByNonveg(foods, isNonveg);
        }
        if (isSeasonal) {
            foods = filterBySeasonal(foods, isSeasonal);
        }
        if (category != null && !category.equals("")) {
            foods = filterByCategory(foods, category);
        }

        return foods;
    }

    private List<Food> filterByCategory(List<Food> foods, String category) {
        return foods.stream()
                .filter(food -> {
                    if (food.getCategory().getName().equals(category)) {
                        return true;
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }

    private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
        return foods.stream().filter(food -> food.isSeasonal() == isSeasonal).collect(Collectors.toList());
    }

    private List<Food> filterByNonveg(List<Food> foods, boolean isNonveg) {
        return foods.stream().filter(food -> food.isVegetarian() == false).collect(Collectors.toList());
    }

    private List<Food> filterByVegetarian(List<Food> foods, boolean isVegetarian) {
        return foods.stream()
                .filter(food -> food.isVegetarian() == isVegetarian)
                .collect(Collectors.toList());
    }

    @Override
    public List<Food> searchFood(String keyword) {
        return foodRepository.searchFood(keyword);
    }

    @Override
    public Food findFoodById(Long foodId) throws Exception {
        Optional<Food> opt = foodRepository.findById(foodId);
        if (opt.isEmpty()) {
            throw new Exception("Food not exist......");
        }

        return opt.get();
    }

    @Override
    public Food updateAvailibityStatus(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setAvailable(!food.isAvailable());

        return foodRepository.save(food);
    }
}
