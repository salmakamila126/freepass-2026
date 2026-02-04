package com.bcc.canteen.controller;

import com.bcc.canteen.entity.*;
import com.bcc.canteen.repository.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {

        if (order.getItems() != null) {
            order.getItems().forEach(item -> item.setOrder(order));
        }

        return orderRepository.save(order);
    }

}
