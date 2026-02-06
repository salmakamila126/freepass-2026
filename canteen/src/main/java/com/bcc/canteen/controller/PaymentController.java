package com.bcc.canteen.controller;

import com.bcc.canteen.entity.*;
import com.bcc.canteen.repository.OrderRepository;
import com.bcc.canteen.repository.PaymentRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentController(PaymentRepository paymentRepository, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    @PostMapping("/pay/{orderId}")
    public Payment payOrder(@PathVariable Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Payment payment = order.getPayment();

        if (payment == null) {
            throw new RuntimeException("Payment not found for this order");
        }

        if (payment.getStatus() == PaymentStatus.PAID) {
            throw new RuntimeException("Order already paid");
        }

        payment.setPaymentTime(LocalDateTime.now());
        payment.setStatus(PaymentStatus.PAID);

        return paymentRepository.save(payment);
    }
}
