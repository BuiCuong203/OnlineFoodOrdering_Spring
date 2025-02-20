package com.FoodOrder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FoodOrder.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByCustomerId(Long userId);
}
