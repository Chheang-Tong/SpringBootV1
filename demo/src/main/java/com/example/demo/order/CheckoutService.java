package com.example.demo.order;

import com.example.demo.cart.Cart;
import com.example.demo.cart.CartItem;
import com.example.demo.cart.CartRepository;
import com.example.demo.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class CheckoutService {
    private final CartRepository carts;
    private final OrderRepository orders;

    public CheckoutService(CartRepository carts, OrderRepository orders) {
        this.carts = carts;
        this.orders = orders;
    }

    @Transactional
    public Order createOrderFromCart(User user) {
        Cart cart = carts.findByUser(user).orElseThrow(() -> new IllegalStateException("Cart empty"));
        if (cart.getItems().isEmpty())
            throw new IllegalStateException("Cart has no items");

        Order o = new Order();
        o.setUser(user);
        o.setSubtotal(cart.getSubtotal());
        o.setShipping(BigDecimal.ZERO);
        o.setTotal(o.getSubtotal().add(o.getShipping()));

        for (CartItem ci : cart.getItems()) {
            OrderItem oi = new OrderItem();
            oi.setOrder(o);
            oi.setProduct(ci.getProduct());
            oi.setQty(ci.getQty());
            oi.setPriceAtPurchase(ci.getProduct().getPrice());
            oi.setLineTotal(ci.getLineTotal());
            o.getItems().add(oi);
        }

        cart.getItems().clear();
        cart.setSubtotal(BigDecimal.ZERO);

        return orders.save(o);
    }
}
