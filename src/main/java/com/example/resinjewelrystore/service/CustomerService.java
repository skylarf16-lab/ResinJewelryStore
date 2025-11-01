package com.example.resinjewelrystore.service;

import com.example.resinjewelrystore.model.Customer;
import com.example.resinjewelrystore.model.Product;
import com.example.resinjewelrystore.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() { return customerRepository.findAll(); }
    public Customer getCustomerById(Long id) { return customerRepository.findById(id).orElseThrow(); }
    public Customer createCustomer(Customer customer) { return customerRepository.save(customer); }
    public Customer updateCustomer(Long id, Customer customer) {
        Customer existing = getCustomerById(id);
        existing.setName(customer.getName());
        existing.setEmail(customer.getEmail());
        return customerRepository.save(existing);
    }
    public void deleteCustomer(Long id) { customerRepository.deleteById(id); }

    // --- Cart Operations ---
    public Customer addToCart(Long customerId, Product product) {
        Customer customer = getCustomerById(customerId);
        customer.addToCart(product);
        return customerRepository.save(customer);
    }

    public Customer removeFromCart(Long customerId, Product product) {
        Customer customer = getCustomerById(customerId);
        customer.removeFromCart(product);
        return customerRepository.save(customer);
    }
}
