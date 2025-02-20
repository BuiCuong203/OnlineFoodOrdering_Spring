package com.FoodOrder.model;

import java.time.LocalDateTime;
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
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @OneToOne
    User owner;

    String name;
    String description;
    String cuisineType;

    @OneToOne
    Address address;

    @Embedded
    ContactInformation contactInformation;

    String openingHours;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "restaurant")
    List<Order> orders = new ArrayList<>();

    @ElementCollection
    @Column(length = 1000)
    List<String> images;

    LocalDateTime registrationDate;
    boolean open;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "restaurant")
    List<Food> food = new ArrayList<>();
}
