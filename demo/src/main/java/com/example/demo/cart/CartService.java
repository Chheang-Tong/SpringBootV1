package com.example.demo.cart;

import com.example.demo.product.Product;
import com.example.demo.product.ProductRepository;
import com.example.demo.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class CartService {
    private final CartRepository carts;
    private final ProductRepository products;

    public CartService(CartRepository carts, ProductRepository products) {
        this.carts = carts;
        this.products = products;
    }

    private Cart getOrCreate(User user) {
        return carts.findByUser(user).orElseGet(() -> {
            Cart c = new Cart();
            c.setUser(user);
            return carts.save(c);
        });
    }

    @Transactional
    public Cart addItem(User user, Long productId, int qty) {
        if (qty <= 0)
            qty = 1;
        Cart cart = getOrCreate(user);
        Product p = products.findById(productId).orElseThrow();
        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId)).findFirst().orElse(null);
        if (item == null) {
            item = new CartItem();
            item.setCart(cart);
            item.setProduct(p);
            item.setQty(qty);
            cart.getItems().add(item);
        } else {
            item.setQty(item.getQty() + qty);
        }
        item.setLineTotal(p.getPrice().multiply(BigDecimal.valueOf(item.getQty())));
        recalc(cart);
        return carts.save(cart);
    }

    @Transactional
    public Cart updateQty(User user, Long productId, int qty) {
        Cart cart = getOrCreate(user);
        if (qty <= 0) {
            cart.getItems().removeIf(i -> i.getProduct().getId().equals(productId));
        } else {
            cart.getItems().forEach(i -> {
                if (i.getProduct().getId().equals(productId)) {
                    i.setQty(qty);
                    i.setLineTotal(i.getProduct().getPrice().multiply(BigDecimal.valueOf(qty)));
                }
            });
        }
        recalc(cart);
        return carts.save(cart);
    }

    @Transactional
    public Cart removeItem(User user, Long productId) {
        Cart cart = getOrCreate(user);
        cart.getItems().removeIf(i -> i.getProduct().getId().equals(productId));
        recalc(cart);
        return carts.save(cart);
    }

    @Transactional
    public void clear(User user) {
        Cart cart = getOrCreate(user);
        cart.getItems().clear();
        cart.setSubtotal(BigDecimal.ZERO);
        carts.save(cart);
    }

    private void recalc(Cart cart) {
        cart.setSubtotal(cart.getItems().stream()
                .map(CartItem::getLineTotal).reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    public Cart view(User user) {
        return getOrCreate(user);
    }
}
