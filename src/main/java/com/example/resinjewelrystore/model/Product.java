package com.example.resinjewelrystore.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "@type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ResinRing.class, name = "Ring"),
        @JsonSubTypes.Type(value = ResinNecklace.class, name = "Necklace"),
        @JsonSubTypes.Type(value = ResinBracelet.class, name = "Bracelet"),
        @JsonSubTypes.Type(value = ResinEarrings.class, name = "Earrings")
})
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String material;
    private Double price;

    // Getters and Setters
    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}