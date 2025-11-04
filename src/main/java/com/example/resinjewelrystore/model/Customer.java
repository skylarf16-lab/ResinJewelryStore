package com.example.resinjewelrystore.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    // Customer's cart mapped to a join table
    @ManyToMany
    @JoinTable(
            name = "customer_cart",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> cart = new ArrayList<>();


    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public List<Product> getCart() { return cart; }


    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setCart(List<Product> cart) { this.cart = cart; }


    public void addToCart(Product product) { cart.add(product); }
    public void removeFromCart(Product product) { cart.remove(product); }

}
