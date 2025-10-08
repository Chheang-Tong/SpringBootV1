package com.example.demo.payment;

import com.example.demo.order.Order;
import com.example.demo.order.OrderRepository;
import com.example.demo.order.OrderStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PaymentService {
    private final PaymentRepository payments;
    private final OrderRepository orders;

    public PaymentService(PaymentRepository payments, OrderRepository orders) {
        this.payments = payments;
        this.orders = orders;
    }

    @Transactional
    public Payment pay(Long orderId, PaymentMethod method) {
        Order order = orders.findById(orderId).orElseThrow();
        if (order.getStatus() != OrderStatus.PENDING)
            throw new IllegalStateException("Order not payable");

        Payment p = new Payment();
        p.setOrder(order);
        p.setMethod(method);
        p.setAmount(order.getTotal());
        p.setStatus(PaymentStatus.CAPTURED);
        p.setProviderRef("MOCK-" + UUID.randomUUID());

        order.setStatus(OrderStatus.PAID);
        payments.save(p);
        return p;
    }
}
