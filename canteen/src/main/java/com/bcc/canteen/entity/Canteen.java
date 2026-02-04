    package com.bcc.canteen.entity;

    import jakarta.persistence.*;
    import java.util.List;
    import com.fasterxml.jackson.annotation.JsonBackReference;
    @Entity
    public class Canteen {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String name;
        @ManyToOne
        private User owner;
        @OneToMany(mappedBy = "canteen", cascade = CascadeType.ALL, orphanRemoval = true)
        @JsonBackReference
        private List<Menu> menus;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public User getOwner() {
            return owner;
        }

        public void setOwner(User owner) {
            this.owner = owner;
        }

        public List<Menu> getMenus() {
            return menus;
        }

        public void setMenus(List<Menu> menus) {
            this.menus = menus;
        }
    }
