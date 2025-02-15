package com.FoodOrder.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddCartItemRequest {
    private Long foodId;
    int quantity;
    List<String> ingredients;
}
