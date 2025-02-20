package com.FoodOrder.service;

import java.util.List;

import com.FoodOrder.model.IngredientCategory;
import com.FoodOrder.model.IngredientsItem;

public interface IngredientsService {
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception;

    public IngredientCategory findIngredientCategoryById(Long id) throws Exception;

    public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception;

    public IngredientsItem createIngredientsItem(String name, Long restaurantId, Long ingredientCategoryId)
            throws Exception;

    public List<IngredientsItem> findIngredientsByRestaurantId(Long restaurantId);

    public IngredientsItem updateStock(Long id) throws Exception;
}
