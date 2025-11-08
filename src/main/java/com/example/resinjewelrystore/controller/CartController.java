package com.example.resinjewelrystore.controller;

import com.example.resinjewelrystore.model.Customer;
import com.example.resinjewelrystore.model.Product;
import com.example.resinjewelrystore.repository.CustomerRepository;
import com.example.resinjewelrystore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    // Get cart
    @GetMapping("/{customerId}")
    public List<Product> getCart(@PathVariable Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with id " + customerId));
        return customer.getCart();
    }

    // Add product
    @PostMapping("/{customerId}/add/{productId}")
    public Customer addProduct(@PathVariable Long customerId, @PathVariable Long productId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with id " + customerId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + productId));
        customer.addToCart(product);
        return customerRepository.save(customer);
    }

    // Add all products
    @PostMapping("/{customerId}/add/all")
    public Customer addAllProducts(@PathVariable Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with id " + customerId));
        List<Product> allProducts = productRepository.findAll();
        allProducts.forEach(customer::addToCart);
        return customerRepository.save(customer);
    }

    // Remove product
    @DeleteMapping("/{customerId}/remove/{productId}")
    public Customer removeProduct(@PathVariable Long customerId, @PathVariable Long productId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with id " + customerId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + productId));
        customer.removeFromCart(product);
        return customerRepository.save(customer);
    }

    // Clear cart
    @DeleteMapping("/{customerId}/clear")
    public Customer clearCart(@PathVariable Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with id " + customerId));
        customer.clearCart();
        return customerRepository.save(customer);
    }

    // Checkout
    @PostMapping("/{customerId}/checkout")
    public String checkout(@PathVariable Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with id " + customerId));

        List<Product> cart = customer.getCart();
        if (cart.isEmpty()) return "Cart is empty!";

        double total = cart.stream().mapToDouble(Product::getPrice).sum();
        customer.clearCart();
        customerRepository.save(customer);

        return "Checkout complete. Total: $" + total;
    }
}