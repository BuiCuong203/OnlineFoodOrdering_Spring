package com.FoodOrder.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateCartItemRequest {
    Long cartItemId;
    int quantity;
}
