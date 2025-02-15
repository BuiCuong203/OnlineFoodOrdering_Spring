package com.FoodOrder.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Embeddable
public class RestaurantDto {
    String title;

    @Column(length = 1000)
    List<String> images;

    String description;
    Long id;
}
