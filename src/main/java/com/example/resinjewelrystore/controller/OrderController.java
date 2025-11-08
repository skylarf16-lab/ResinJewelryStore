package com.example.resinjewelrystore.controller;

import com.example.resinjewelrystore.model.Customer;
import com.example.resinjewelrystore.model.Order;
import com.example.resinjewelrystore.model.OrderStatus;
import com.example.resinjewelrystore.model.Product;
import com.example.resinjewelrystore.repository.CustomerRepository;
import com.example.resinjewelrystore.repository.OrderRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    public OrderController(OrderRepository orderRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id " + id));
    }

    @PostMapping
    public Order createOrder(@RequestBody Order orderRequest) {
        Customer customer = customerRepository.findById(orderRequest.getCustomer().getId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // Copy products from customer's cart
        List<Product> cartProducts = new ArrayList<>(customer.getCart());
        if (cartProducts.isEmpty()) {
            throw new RuntimeException("Customer cart is empty");
        }

        double total = cartProducts.stream().mapToDouble(Product::getPrice).sum();

        Order order = new Order();
        order.setCustomer(customer);
        order.setProducts(cartProducts);
        order.setTotalAmount(total);
        order.setStatus(OrderStatus.valueOf("PENDING"));

        // Optionally clear the cart after creating order
        customer.clearCart();
        customerRepository.save(customer);

        return orderRepository.save(order);
    }

    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable Long id, @RequestBody Order updatedOrder) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (updatedOrder.getStatus() != null) {
            order.setStatus(updatedOrder.getStatus());
        }
        return orderRepository.save(order);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderRepository.deleteById(id);
    }
}