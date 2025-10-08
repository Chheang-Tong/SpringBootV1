package com.example.demo.order;

import com.example.demo.common.CurrentUser;
import com.example.demo.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final CheckoutService checkout;
    private final OrderRepository orders;
    private final CurrentUser current;

    public OrderController(CheckoutService checkout, OrderRepository orders, CurrentUser current) {
        this.checkout = checkout;
        this.orders = orders;
        this.current = current;
    }

    @PostMapping("/checkout")
    public Order checkout(Authentication auth) {
        User u = current.require(auth);
        return checkout.createOrderFromCart(u);
    }

    @GetMapping
    public List<Order> mine(Authentication auth) {
        User u = current.require(auth);
        return orders.findByUserOrderByCreatedAtDesc(u);
    }

    @GetMapping("/{id}")
    public Order get(@PathVariable Long id) {
        return orders.findById(id).orElseThrow();
    }
}
