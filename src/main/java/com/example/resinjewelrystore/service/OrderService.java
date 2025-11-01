package com.example.resinjewelrystore.service;

import com.example.resinjewelrystore.model.Order;
import com.example.resinjewelrystore.repository.OrderRepository;
import com.example.resinjewelrystore.exception.OrderNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    public OrderService(OrderRepository orderRepository) { this.orderRepository = orderRepository; }

    public List<Order> getAllOrders() { return orderRepository.findAll(); }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }

    public Order createOrder(Order order) { return orderRepository.save(order); }

    public Order updateOrder(Long id, Order order) {
        Order existing = getOrderById(id);
        existing.setCustomer(order.getCustomer());
        existing.setProducts(order.getProducts());
        existing.setTotalAmount(order.getTotalAmount());
        existing.setStatus(order.getStatus());
        existing.setOrderDate(order.getOrderDate());
        return orderRepository.save(existing);
    }

    public void deleteOrder(Long id) { orderRepository.deleteById(id); }
}