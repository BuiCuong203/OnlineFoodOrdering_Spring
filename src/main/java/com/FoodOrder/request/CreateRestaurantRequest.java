package com.FoodOrder.request;

import com.FoodOrder.model.Address;
import com.FoodOrder.model.ContactInformation;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateRestaurantRequest {
    Long id;
    String name;
    String description;
    String cuisineType;
    Address address;
    ContactInformation contactInformation;
    String openingHours;
    List<String> images;
}
