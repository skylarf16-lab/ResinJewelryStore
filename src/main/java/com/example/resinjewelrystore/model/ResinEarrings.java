package com.example.resinjewelrystore.model;

import jakarta.persistence.Entity;

@Entity
public class ResinEarrings extends Product {
    private boolean isStud; // true if stud, false if dangling

    // getters and setters
    public boolean isStud() { return isStud; }
    public void setStud(boolean isStud) { this.isStud = isStud; }
}

