package com.example.resinjewelrystore.config;

import com.example.resinjewelrystore.model.*;
import com.example.resinjewelrystore.repository.CustomerRepository;
import com.example.resinjewelrystore.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Random;

@Component
public class JewelryDataLoader {

    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final Random random = new Random();

    public JewelryDataLoader(ProductRepository productRepository, CustomerRepository customerRepository) {
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    @PostConstruct
    public void loadData() {

        // 1️⃣ Create products
        Product necklace = new ResinNecklace();
        necklace.setName("Ocean Blue Necklace");
        necklace.setMaterial("Resin");
        necklace.setPrice(35.0);

        Product ring = new ResinRing();
        ring.setName("Golden Glitter Ring");
        ring.setMaterial("Resin");
        ring.setPrice(25.0);

        Product bracelet = new ResinBracelet();
        bracelet.setName("Sunset Pink Bracelet");
        bracelet.setMaterial("Resin");
        bracelet.setPrice(30.0);

        Product earrings = new ResinEarrings();
        earrings.setName("Silver Sparkle Earrings");
        earrings.setMaterial("Resin");
        earrings.setPrice(28.0);

        // Save products first
        List<Product> savedProducts = productRepository.saveAll(List.of(necklace, ring, bracelet, earrings));

        // 2️⃣ Create customers with random carts
        createCustomerWithRandomCart("Alice Smith", "alice@example.com", savedProducts);
        createCustomerWithRandomCart("Bob Johnson", "bob@example.com", savedProducts);
        createCustomerWithRandomCart("Carol Davis", "carol@example.com", savedProducts);
    }

    private void createCustomerWithRandomCart(String name, String email, List<Product> products) {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);

        // Add 1-3 random products to the cart
        int numItems = random.nextInt(3) + 1;
        for (int i = 0; i < numItems; i++) {
            Product product = products.get(random.nextInt(products.size()));
            customer.addToCart(product);
        }

        customerRepository.save(customer); // ✅ Safe now, no detached entity error
    }
}