package com.FoodOrder.service;

import java.util.List;

import com.FoodOrder.model.Category;

public interface CategoryService {
    public Category createCategory(String name, Long userId) throws Exception;

    public List<Category> findCategoryByRestaurantId(Long restaurantId) throws Exception;

    public Category findCategoryById(Long id) throws Exception;
}
