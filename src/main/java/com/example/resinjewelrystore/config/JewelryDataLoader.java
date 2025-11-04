package com.example.resinjewelrystore.config;

import com.example.resinjewelrystore.model.*;
import com.example.resinjewelrystore.repository.CustomerRepository;
import com.example.resinjewelrystore.repository.ProductRepository;
import com.example.resinjewelrystore.repository.OrderRepository;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
public class JewelryDataLoader {

    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    public JewelryDataLoader(ProductRepository productRepository,
                      CustomerRepository customerRepository,
                      OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }

    @PostConstruct
    public void loadData() {
        // --- Sample Products ---
        if (productRepository.count() == 0) {
            ResinRing ring = new ResinRing();
            ring.setName("Golden Glitter Ring");
            ring.setMaterial("Resin");
            ring.setPrice(25.0);

            ResinNecklace necklace = new ResinNecklace();
            necklace.setName("Ocean Blue Necklace");
            necklace.setMaterial("Resin");
            necklace.setPrice(35.0);

            ResinBracelet bracelet = new ResinBracelet();
            bracelet.setName("Sunset Pink Bracelet");
            bracelet.setMaterial("Resin");
            bracelet.setPrice(30.0);

            ResinEarrings earrings = new ResinEarrings();
            earrings.setName("Silver Sparkle Earrings");
            earrings.setMaterial("Resin");
            earrings.setPrice(28.0);

            productRepository.saveAll(Arrays.asList(ring, necklace, bracelet, earrings));
        }

        List<Product> products = productRepository.findAll();

        // --- Sample Customers with pre-filled carts ---
        if (customerRepository.count() == 0) {
            Customer alice = new Customer();
            alice.setName("Alice Johnson");
            alice.setEmail("alice@example.com");
            alice.setCart(Arrays.asList(products.get(0), products.get(1))); // ring + necklace

            Customer bob = new Customer();
            bob.setName("Bob Smith");
            bob.setEmail("bob@example.com");
            bob.setCart(Arrays.asList(products.get(2), products.get(3))); // bracelet + earrings

            customerRepository.saveAll(Arrays.asList(alice, bob));
        }

        // --- Optional Sample Order ---
        if (orderRepository.count() == 0) {
            Customer alice = customerRepository.findById(1L).orElse(null);
            if (alice != null) {
                Order order = new Order();
                order.setCustomer(alice);
                order.setProducts(alice.getCart());
                order.setTotalAmount(alice.getCart().stream().mapToDouble(Product::getPrice).sum());
                order.setStatus(OrderStatus.PENDING);
                order.setOrderDate(LocalDateTime.now());
                orderRepository.save(order);
            }
        }
    }
}