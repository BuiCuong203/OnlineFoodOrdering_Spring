package com.FoodOrder.request;

import java.util.List;

import com.FoodOrder.model.Category;
import com.FoodOrder.model.IngredientsItem;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateFoodRequest {
    String name;
    String description;
    Long price;
    List<String> images;
    Category category;
    Long restaurantId;
    boolean vegetarian;
    boolean seasional;
    List<IngredientsItem> ingredientsItems;
}
