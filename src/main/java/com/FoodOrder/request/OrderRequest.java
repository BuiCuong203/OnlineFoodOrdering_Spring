package com.FoodOrder.request;

import com.FoodOrder.model.Address;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequest {
    Long restaurantId;
    String methodPayment;
    Address deliveryAddress;
}
