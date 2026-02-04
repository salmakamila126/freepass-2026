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

    //Make payment for an order
    @PostMapping("/pay/{orderId}")
    public Payment payOrder(@PathVariable Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getPayment() != null && order.getPayment().getStatus() == PaymentStatus.PAID) {
            throw new RuntimeException("Order already paid");
        }

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(order.getTotalPrice());
        payment.setPaymentTime(LocalDateTime.now());
        payment.setStatus(PaymentStatus.PAID);

        order.setPayment(payment);

        return paymentRepository.save(payment);
    }
}
