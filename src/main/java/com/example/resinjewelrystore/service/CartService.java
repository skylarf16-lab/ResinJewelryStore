package com.example.resinjewelrystore.service;

import com.example.resinjewelrystore.model.Customer;
import com.example.resinjewelrystore.model.Product;
import com.example.resinjewelrystore.model.Order;
import com.example.resinjewelrystore.model.OrderStatus;
import com.example.resinjewelrystore.repository.CustomerRepository;
import com.example.resinjewelrystore.repository.ProductRepository;
import com.example.resinjewelrystore.repository.OrderRepository;
import com.example.resinjewelrystore.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public CartService(CustomerRepository customerRepository,
                       ProductRepository productRepository,
                       OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public List<Product> getCart(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow();
        return customer.getCart();
    }

    public List<Product> addToCart(Long customerId, Long productId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        customer.addToCart(product);
        customerRepository.save(customer);
        return customer.getCart();
    }

    public List<Product> removeFromCart(Long customerId, Long productId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        customer.removeFromCart(product);
        customerRepository.save(customer);
        return customer.getCart();
    }

    public Order checkout(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow();
        List<Product> cart = customer.getCart();
        if (cart.isEmpty()) throw new RuntimeException("Cart is empty");

        double total = cart.stream().mapToDouble(Product::getPrice).sum();
        Order order = new Order();
        order.setCustomer(customer);
        order.setProducts(new ArrayList<>(cart));
        order.setTotalAmount(total);
        order.setStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDateTime.now());

        orderRepository.save(order);

        customer.setCart(new ArrayList<>());
        customerRepository.save(customer);

        return order;
    }
}