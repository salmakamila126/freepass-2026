package com.bcc.canteen.controller;

import com.bcc.canteen.dto.CreateOrderRequest;
import com.bcc.canteen.entity.Order;
import com.bcc.canteen.entity.OrderStatus;
import com.bcc.canteen.entity.User;
import com.bcc.canteen.service.OrderService;
import com.bcc.canteen.util.AuthUtil;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByUser(@PathVariable Long userId,
                                       @AuthenticationPrincipal User currentUser) {
        AuthUtil.checkUser(currentUser);
        return orderService.getOrdersByUser(userId);
    }
    @GetMapping("/canteen/{canteenId}")
    public List<Order> getOrdersByCanteen(@PathVariable Long canteenId,
                                          @AuthenticationPrincipal User currentUser) {
        AuthUtil.checkOwner(currentUser);
        return orderService.getOrdersByCanteen(canteenId);
    }
    @PostMapping
    public Order createOrder(@RequestBody CreateOrderRequest request,
                             @AuthenticationPrincipal User currentUser) {
        AuthUtil.checkUser(currentUser);
        return orderService.createOrder(request);
    }
    @PutMapping("/status/{orderId}")
    public Order updateOrderStatus(@PathVariable Long orderId,
                                   @RequestParam OrderStatus status,
                                   @AuthenticationPrincipal User currentUser) {
        AuthUtil.checkOwner(currentUser);
        return orderService.updateOrderStatus(orderId, status);
    }
}
