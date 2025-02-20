package com.FoodOrder.model;

import java.util.ArrayList;
import java.util.List;

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
public class IngredientCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String name;

    @JsonIgnore
    @ManyToOne
    Restaurant restaurant;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ingredientCategory")
    List<IngredientsItem> ingredients = new ArrayList<>();
}
