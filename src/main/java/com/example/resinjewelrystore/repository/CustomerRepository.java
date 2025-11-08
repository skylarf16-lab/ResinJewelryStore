package com.example.resinjewelrystore.repository;

import com.example.resinjewelrystore.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // Return Optional to use orElseGet/orElseThrow
    Optional<Customer> findByEmail(String email);

    // Check if a customer exists by email
    boolean existsByEmail(String email);
}