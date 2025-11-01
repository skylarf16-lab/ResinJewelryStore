package com.example.resinjewelrystore.service;

import com.example.resinjewelrystore.model.*;
import com.example.resinjewelrystore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() { return productRepository.findAll(); }

    public Product createProduct(Product product) { return productRepository.save(product); }

    @PostConstruct
    public void initSampleProducts() {
        if (productRepository.count() == 0) {

            ResinNecklace necklace = new ResinNecklace();
            necklace.setName("Ocean Blue Necklace");
            necklace.setMaterial("Resin");
            necklace.setPrice(35.0);
            productRepository.save(necklace);

            ResinRing ring = new ResinRing();
            ring.setName("Golden Glitter Ring");
            ring.setMaterial("Resin");
            ring.setPrice(25.0);
            productRepository.save(ring);

            ResinBracelet bracelet = new ResinBracelet();
            bracelet.setName("Sunset Pink Bracelet");
            bracelet.setMaterial("Resin");
            bracelet.setPrice(30.0);
            productRepository.save(bracelet);

            ResinEarrings earrings = new ResinEarrings();
            earrings.setName("Silver Sparkle Earrings");
            earrings.setMaterial("Resin");
            earrings.setPrice(28.0);
            productRepository.save(earrings);
        }
    }
}