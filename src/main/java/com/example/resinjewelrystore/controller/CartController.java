package com.example.resinjewelrystore.controller;

import com.example.resinjewelrystore.model.Order;
import com.example.resinjewelrystore.model.Product;
import com.example.resinjewelrystore.service.CartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{customerId}")
    public List<Product> getCart(@PathVariable Long customerId) {
        return cartService.getCart(customerId);
    }

    @PostMapping("/{customerId}/add/{productId}")
    public List<Product> addToCart(@PathVariable Long customerId, @PathVariable Long productId) {
        return cartService.addToCart(customerId, productId);
    }

    @PostMapping("/{customerId}/remove/{productId}")
    public List<Product> removeFromCart(@PathVariable Long customerId, @PathVariable Long productId) {
        return cartService.removeFromCart(customerId, productId);
    }

    @PostMapping("/{customerId}/checkout")
    public Order checkout(@PathVariable Long customerId) {
        return cartService.checkout(customerId);
    }
}