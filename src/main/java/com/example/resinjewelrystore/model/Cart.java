package com.example.resinjewelrystore.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Customer customer;

    @ManyToMany
    private List<Product> products = new ArrayList<>();

    // Getters and Setters
    public Long getId() { return id; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public List<Product> getProducts() { return products; }
    public void setProducts(List<Product> products) { this.products = products; }

    public void addProduct(Product product) { this.products.add(product); }
    public void removeProduct(Product product) { this.products.remove(product); }
}