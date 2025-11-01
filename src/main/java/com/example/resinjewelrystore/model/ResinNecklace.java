package com.example.resinjewelrystore.model;

import jakarta.persistence.Entity;

@Entity
public class ResinNecklace extends Product {
    private String chainMaterial;
    private Double length;

}
