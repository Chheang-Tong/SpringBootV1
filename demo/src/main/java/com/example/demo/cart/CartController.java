package com.example.demo.cart;

import com.example.demo.common.CurrentUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService carts;
    private final CurrentUser current;

    public CartController(CartService carts, CurrentUser current) {
        this.carts = carts;
        this.current = current;
    }

    @GetMapping
    public Cart view(Authentication auth) {
        return carts.view(current.require(auth));
    }

    @PostMapping("/items/{productId}")
    public ResponseEntity<Cart> add(Authentication auth, @PathVariable Long productId,
            @RequestParam(defaultValue = "1") int qty) {
        return ResponseEntity.ok(carts.addItem(current.require(auth), productId, qty));
    }

    @PatchMapping("/items/{productId}")
    public Cart updateQty(Authentication auth, @PathVariable Long productId, @RequestParam int qty) {
        return carts.updateQty(current.require(auth), productId, qty);
    }

    @DeleteMapping("/items/{productId}")
    public Cart remove(Authentication auth, @PathVariable Long productId) {
        return carts.removeItem(current.require(auth), productId);
    }

    @DeleteMapping
    public void clear(Authentication auth) {
        carts.clear(current.require(auth));
    }
}
