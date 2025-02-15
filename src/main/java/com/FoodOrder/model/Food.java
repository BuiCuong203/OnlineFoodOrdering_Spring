package com.FoodOrder.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String name;
    String description;
    Long price;

    @ManyToOne
    Category category;

    @Column(length = 1000)
    @ElementCollection
    List<String> images;

    boolean available;

    @ManyToOne
    Restaurant restaurant;

    boolean isVegetarian;
    boolean isSeasonal;

    @ManyToMany
    List<IngredientsItem> ingredientsItems = new ArrayList<>();

    LocalDateTime creationDate;
}
