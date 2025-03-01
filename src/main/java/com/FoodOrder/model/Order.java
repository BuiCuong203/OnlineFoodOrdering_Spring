package com.FoodOrder.model;

import java.util.Date;
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

    String orderStatus;
    Date createdAt;

    @ManyToOne
    Address deliveryAddress;

    @OneToMany
    List<OrderItem> items;

    int totalItem;
    Long totalPrice;
    String methodPayment;
}
