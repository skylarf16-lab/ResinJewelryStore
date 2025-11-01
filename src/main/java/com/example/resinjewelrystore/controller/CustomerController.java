package com.example.resinjewelrystore.controller;

import com.example.resinjewelrystore.model.Customer;
import com.example.resinjewelrystore.model.Product;
import com.example.resinjewelrystore.service.CustomerService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;
    public CustomerController(CustomerService customerService) { this.customerService = customerService; }

    @GetMapping
    public List<Customer> getAllCustomers() { return customerService.getAllCustomers(); }

    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable Long id) { return customerService.getCustomerById(id); }

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) { return customerService.createCustomer(customer); }

    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        return customerService.updateCustomer(id, customer);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) { customerService.deleteCustomer(id); }

    // --- Cart Endpoints ---
    @PostMapping("/{id}/cart")
    public Customer addToCart(@PathVariable Long id, @RequestBody Product product) {
        return customerService.addToCart(id, product);
    }

    @DeleteMapping("/{id}/cart")
    public Customer removeFromCart(@PathVariable Long id, @RequestBody Product product) {
        return customerService.removeFromCart(id, product);
    }
}