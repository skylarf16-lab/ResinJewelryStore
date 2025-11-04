package com.example.resinjewelrystore.repository;

import com.example.resinjewelrystore.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // Find a customer by email
    Customer findByEmail(String email);

    // Optional: check if a customer exists by email
    boolean existsByEmail(String email);
}
