package com.FoodOrder.request;

import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddCartItemRequest {
    private Long foodId;
    int quantity;
    List<String> ingredients;
}
