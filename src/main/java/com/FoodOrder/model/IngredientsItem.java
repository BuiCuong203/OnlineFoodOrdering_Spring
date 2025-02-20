package com.FoodOrder.model;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class IngredientsItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String name;

    @JsonIgnore
    @ManyToOne
    IngredientCategory ingredientCategory;

    @JsonIgnore
    @ManyToOne
    Restaurant restaurant;

    boolean inStoke = true;
}
