package com.example.resinjewelrystore.repository;

import com.example.resinjewelrystore.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {}
