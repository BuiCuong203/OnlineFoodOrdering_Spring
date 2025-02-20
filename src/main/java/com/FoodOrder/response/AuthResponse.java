package com.FoodOrder.response;

import com.FoodOrder.model.USER_ROLE;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthResponse {
    String jwt;
    String message;
    USER_ROLE role;
}
