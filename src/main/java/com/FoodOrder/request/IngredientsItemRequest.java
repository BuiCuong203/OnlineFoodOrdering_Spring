package com.FoodOrder.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IngredientsItemRequest {
    String name;
    Long restaurantId;
    Long ingredientCategoryId;
}
