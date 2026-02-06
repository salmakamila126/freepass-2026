    package com.bcc.canteen.service;

    import com.bcc.canteen.dto.CreateOrderRequest;
    import com.bcc.canteen.dto.OrderItemRequest;
    import com.bcc.canteen.entity.*;
    import com.bcc.canteen.repository.*;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;
    import java.util.ArrayList;
    import java.util.List;

    @Service
    public class OrderService {

        private final OrderRepository orderRepository;
        private final UserRepository userRepository;
        private final CanteenRepository canteenRepository;
        private final MenuRepository menuRepository;
        private final PaymentRepository paymentRepository;

        public OrderService(OrderRepository orderRepository,
                            UserRepository userRepository,
                            CanteenRepository canteenRepository,
                            MenuRepository menuRepository,
                            PaymentRepository paymentRepository) {
            this.orderRepository = orderRepository;
            this.userRepository = userRepository;
            this.canteenRepository = canteenRepository;
            this.menuRepository = menuRepository;
            this.paymentRepository = paymentRepository;
        }

        @Transactional
        public Order createOrder(CreateOrderRequest request) {

            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Canteen canteen = canteenRepository.findById(request.getCanteenId())
                    .orElseThrow(() -> new RuntimeException("Canteen not found"));

            Order order = new Order();
            order.setUser(user);
            order.setCanteen(canteen);
            order.setStatus(OrderStatus.PENDING);

            List<OrderItem> orderItems = new ArrayList<>();
            double totalPrice = 0;

            for (OrderItemRequest itemRequest : request.getItems()) {

                Menu menu = menuRepository.findById(itemRequest.getMenuId())
                        .orElseThrow(() -> new RuntimeException("Menu not found"));

                if (menu.getStock() < itemRequest.getQuantity()) {
                    throw new RuntimeException("Not enough stock for " + menu.getName());
                }

                menu.setStock(menu.getStock() - itemRequest.getQuantity());
                menuRepository.save(menu);

                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);
                orderItem.setMenu(menu);
                orderItem.setQuantity(itemRequest.getQuantity());
                orderItem.setPrice(menu.getPrice() * itemRequest.getQuantity());

                totalPrice += orderItem.getPrice();
                orderItems.add(orderItem);
            }

            order.setItems(orderItems);
            order.setTotalPrice(totalPrice);

            Order savedOrder = orderRepository.save(order);

            Payment payment = new Payment();
            payment.setOrder(savedOrder);
            payment.setAmount(totalPrice);
            payment.setStatus(PaymentStatus.UNPAID);

            paymentRepository.save(payment);

            savedOrder.setPayment(payment);

            return savedOrder;
        }
        public List<Order> getOrdersByUser(Long userId) {
            return orderRepository.findByUserId(userId);
        }
        public List<Order> getOrdersByCanteen(Long canteenId) {
            return orderRepository.findByCanteenId(canteenId);
        }

        @Transactional
        public Order updateOrderStatus(Long orderId, OrderStatus status) {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found"));

            if (order.getPayment().getStatus() != PaymentStatus.PAID) {
                throw new RuntimeException("Cannot update order status: payment not completed");
            }

            order.setStatus(status);
            return orderRepository.save(order);
        }
    }
