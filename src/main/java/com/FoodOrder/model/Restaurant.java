package com.FoodOrder.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
