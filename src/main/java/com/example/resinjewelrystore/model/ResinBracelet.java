package com.example.resinjewelrystore.model;

import jakarta.persistence.Entity;

@Entity
public class ResinBracelet extends Product {
    private String size; // Small, Medium, Large

    // getters and setters
    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }
}

