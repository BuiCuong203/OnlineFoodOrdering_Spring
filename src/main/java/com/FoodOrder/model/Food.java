package com.FoodOrder.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Date;
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

    @JsonIgnore
    @ManyToOne
    Restaurant restaurant;

    boolean isVegetarian;
    boolean isSeasonal;

    @ManyToMany
    List<IngredientsItem> ingredientsItems = new ArrayList<>();

    Date creationDate;
}
