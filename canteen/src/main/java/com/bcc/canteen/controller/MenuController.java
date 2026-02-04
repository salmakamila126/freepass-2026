package com.bcc.canteen.controller;

import com.bcc.canteen.entity.Menu;
import com.bcc.canteen.repository.MenuRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/menus")
public class MenuController {

    private final MenuRepository menuRepository;

    public MenuController(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @GetMapping
    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Menu> getMenuById(@PathVariable Long id) {
        return menuRepository.findById(id);
    }

    @PostMapping
    public Menu createMenu(@RequestBody Menu menu) {
        return menuRepository.save(menu);
    }

    @PutMapping("/{id}")
    public Menu updateMenu(@PathVariable Long id, @RequestBody Menu menuDetails) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu not found"));

        menu.setName(menuDetails.getName());
        menu.setPrice(menuDetails.getPrice());
        menu.setStock(menuDetails.getStock());
        menu.setCanteen(menuDetails.getCanteen());

        return menuRepository.save(menu);
    }

    @DeleteMapping("/{id}")
    public void deleteMenu(@PathVariable Long id) {
        menuRepository.deleteById(id);
    }
}