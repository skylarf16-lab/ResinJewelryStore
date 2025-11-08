package com.example.resinjewelrystore.service;

import com.example.resinjewelrystore.model.Customer;
import com.example.resinjewelrystore.model.Order;
import com.example.resinjewelrystore.model.OrderStatus;
import com.example.resinjewelrystore.model.Product;
import com.example.resinjewelrystore.repository.CustomerRepository;
import com.example.resinjewelrystore.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id " + id));
    }

    // Create an order from customer's cart
    public Order createOrder(Order orderRequest) {
        if (orderRequest.getCustomer() == null || orderRequest.getCustomer().getId() == null) {
            throw new RuntimeException("Customer ID is required to create an order.");
        }

        Long customerId = orderRequest.getCustomer().getId();
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with id " + customerId));

        List<Product> cartProducts = customer.getCart();
        if (cartProducts.isEmpty()) {
            throw new RuntimeException("Cannot create order. Cart is empty!");
        }

        double total = cartProducts.stream().mapToDouble(Product::getPrice).sum();

        Order newOrder = new Order();
        newOrder.setCustomer(customer);
        newOrder.setProducts(cartProducts);
        newOrder.setTotalAmount(total);
        newOrder.setStatus(OrderStatus.valueOf("PENDING"));
        newOrder.setOrderDate(LocalDateTime.now());

        Order savedOrder = orderRepository.save(newOrder);

        // clear cart after checkout
        customer.getCart().clear();
        customerRepository.save(customer);

        return savedOrder;
    }

    public Order updateOrder(Long id, Order updatedOrder) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id " + id));

        existingOrder.setStatus(updatedOrder.getStatus());
        return orderRepository.save(existingOrder);
    }

    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Order not found with id " + id);
        }
        orderRepository.deleteById(id);
    }
}