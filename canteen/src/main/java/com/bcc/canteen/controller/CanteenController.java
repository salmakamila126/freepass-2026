package com.bcc.canteen.controller;

import com.bcc.canteen.entity.Canteen;
import com.bcc.canteen.entity.User;
import com.bcc.canteen.entity.Order;
import com.bcc.canteen.repository.CanteenRepository;
import com.bcc.canteen.repository.UserRepository;
import com.bcc.canteen.repository.OrderRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/canteens")
public class CanteenController {

    private final CanteenRepository canteenRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public CanteenController(CanteenRepository canteenRepository, UserRepository userRepository, OrderRepository orderRepository) {
        this.canteenRepository = canteenRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping
    public List<Canteen> getAllCanteens() {
        return canteenRepository.findAll();
    }
    @GetMapping("/{id}")
    public Canteen getCanteenById(@PathVariable Long id) {
        return canteenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Canteen not found"));
    }
    @PostMapping
    public Canteen createCanteen(@RequestBody Canteen canteen) {
        if (canteen.getOwner() != null && canteen.getOwner().getId() != null) {
            User owner = userRepository.findById(canteen.getOwner().getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            canteen.setOwner(owner);
        } else {
            canteen.setOwner(null); // owner optional
        }
        return canteenRepository.save(canteen);
    }
    @PutMapping("/{id}")
    public Canteen updateCanteen(@PathVariable Long id, @RequestBody Canteen canteenDetails) {
        Canteen canteen = canteenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Canteen not found"));

        canteen.setName(canteenDetails.getName());

        if (canteenDetails.getOwner() != null && canteenDetails.getOwner().getId() != null) {
            User owner = userRepository.findById(canteenDetails.getOwner().getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            canteen.setOwner(owner);
        }

        return canteenRepository.save(canteen);
    }
    @DeleteMapping("/{id}")
    public void deleteCanteen(@PathVariable Long id) {
        Canteen canteen = canteenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Canteen not found"));
        if (!canteen.getMenus().isEmpty()) {
            throw new RuntimeException("Cannot delete canteen with menus");
        }
        if (!orderRepository.findByCanteenId(id).isEmpty()) {
            throw new RuntimeException("Cannot delete canteen with orders");
        }

        canteenRepository.delete(canteen);
    }

}

