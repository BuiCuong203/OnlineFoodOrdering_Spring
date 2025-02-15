package com.FoodOrder.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    User customer;

    @JsonIgnore
    @ManyToOne
    Restaurant restaurant;

    long totalAmount;
    String orderStatus;
    Date createdAt;

    @ManyToOne
    Address deliveryAddress;

    @OneToMany
    List<OrderItem> items;

    int totalIteml;
    int totalPrice;
}
