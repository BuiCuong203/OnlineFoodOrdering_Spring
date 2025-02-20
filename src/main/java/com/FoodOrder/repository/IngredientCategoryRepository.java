package com.FoodOrder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FoodOrder.model.IngredientCategory;

@Repository
public interface IngredientCategoryRepository extends JpaRepository<IngredientCategory, Long> {
    List<IngredientCategory> findByRestaurantId(Long restaurantId);
}
