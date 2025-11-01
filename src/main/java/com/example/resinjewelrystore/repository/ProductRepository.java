package com.example.resinjewelrystore.repository;

import com.example.resinjewelrystore.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {}