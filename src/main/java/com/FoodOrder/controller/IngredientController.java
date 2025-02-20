package com.FoodOrder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.FoodOrder.model.IngredientCategory;
import com.FoodOrder.model.IngredientsItem;
import com.FoodOrder.request.IngredientCategoryRequest;
import com.FoodOrder.request.IngredientsItemRequest;
import com.FoodOrder.service.IngredientsService;

@RestController
@RequestMapping("/api/admin/ingredients")
public class IngredientController {

    @Autowired
    private IngredientsService ingredientsService;

    @PostMapping("/category")
    public ResponseEntity<IngredientCategory> createIngredientCategory(@RequestBody IngredientCategoryRequest request)
            throws Exception {
        IngredientCategory ingredientCategory =
                ingredientsService.createIngredientCategory(request.getName(), request.getRestaurantId());

        return new ResponseEntity<>(ingredientCategory, HttpStatus.CREATED);
    }

    @PostMapping()
    public ResponseEntity<IngredientsItem> createIngredientsItem(@RequestBody IngredientsItemRequest request)
            throws Exception {
        IngredientsItem ingredientsItem = ingredientsService.createIngredientsItem(
                request.getName(), request.getRestaurantId(), request.getIngredientCategoryId());

        return new ResponseEntity<>(ingredientsItem, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/stoke")
    public ResponseEntity<IngredientsItem> updateIngredientStock(@PathVariable Long id) throws Exception {
        IngredientsItem ingredientsItem = ingredientsService.updateStock(id);

        return new ResponseEntity<>(ingredientsItem, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<IngredientsItem>> getIngredientRestaurant(@PathVariable Long id) throws Exception {
        List<IngredientsItem> ingredientsItems = ingredientsService.findIngredientsByRestaurantId(id);

        return new ResponseEntity<>(ingredientsItems, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}/category")
    public ResponseEntity<List<IngredientCategory>> getIngredientCategoryRestaurant(@PathVariable Long id)
            throws Exception {
        List<IngredientCategory> ingredientsCategory = ingredientsService.findIngredientCategoryByRestaurantId(id);

        return new ResponseEntity<>(ingredientsCategory, HttpStatus.OK);
    }
}
