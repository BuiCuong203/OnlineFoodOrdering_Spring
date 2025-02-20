package com.FoodOrder.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @OneToOne
    User customer;

    Long total;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "cart")
    List<CartItem> cartItems = new ArrayList<>();
}
