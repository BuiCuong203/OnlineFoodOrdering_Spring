package com.FoodOrder.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FoodOrder.model.IngredientCategory;
import com.FoodOrder.model.IngredientsItem;
import com.FoodOrder.model.Restaurant;
import com.FoodOrder.repository.IngredientCategoryRepository;
import com.FoodOrder.repository.IngredientsItemRepository;

@Service
public class IngredientsServiceImp implements IngredientsService {

    @Autowired
    private IngredientCategoryRepository ingredientCategoryRepository;

    @Autowired
    private IngredientsItemRepository ingredientsItemRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Override
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientCategory ingredientCategory =
                IngredientCategory.builder().name(name).restaurant(restaurant).build();

        return ingredientCategoryRepository.save(ingredientCategory);
    }

    @Override
    public IngredientCategory findIngredientCategoryById(Long id) throws Exception {
        Optional<IngredientCategory> opt = ingredientCategoryRepository.findById(id);
        if (opt.isEmpty()) {
            throw new Exception("Ingredient category not found");
        }

        return opt.get();
    }

    @Override
    public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception {
        return ingredientCategoryRepository.findByRestaurantId(id);
    }

    @Override
    public IngredientsItem createIngredientsItem(String name, Long restaurantId, Long ingredientCategoryId)
            throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientCategory ingredientCategory = findIngredientCategoryById(ingredientCategoryId);
        IngredientsItem ingredientsItem = IngredientsItem.builder()
                .name(name)
                .restaurant(restaurant)
                .ingredientCategory(ingredientCategory)
                .build();
        ingredientCategory.getIngredients().add(ingredientsItem);

        return ingredientsItemRepository.save(ingredientsItem);
    }

    @Override
    public List<IngredientsItem> findIngredientsByRestaurantId(Long restaurantId) {
        return ingredientsItemRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItem updateStock(Long id) throws Exception {
        Optional<IngredientsItem> opt = ingredientsItemRepository.findById(id);
        if (opt.isEmpty()) {
            throw new Exception("IngredientsItem not found");
        }
        IngredientsItem ingredientsItem = opt.get();
        ingredientsItem.setInStoke(!ingredientsItem.isInStoke());

        return ingredientsItemRepository.save(ingredientsItem);
    }
}
