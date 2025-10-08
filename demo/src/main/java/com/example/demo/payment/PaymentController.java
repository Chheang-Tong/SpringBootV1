package com.example.demo.payment;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService payments;

    public PaymentController(PaymentService payments) {
        this.payments = payments;
    }

    @PostMapping("/pay/{orderId}")
    public ResponseEntity<Payment> pay(@PathVariable Long orderId,
            @RequestParam(defaultValue = "CARD") PaymentMethod method) {
        return ResponseEntity.ok(payments.pay(orderId, method));
    }
}
