package com.FoodOrder.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import com.FoodOrder.dto.RestaurantDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String fullname;
    String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;

    USER_ROLE role = USER_ROLE.ROLE_CUSTOMER;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    List<Order> orders = new ArrayList<>();

    @ElementCollection
    List<RestaurantDto> favourites = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    List<Address> addresses = new ArrayList<>();
}
