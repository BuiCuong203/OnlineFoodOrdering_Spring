package com.FoodOrder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FoodOrder.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {}
