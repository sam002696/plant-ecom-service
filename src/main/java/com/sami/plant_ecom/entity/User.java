package com.sami.plant_ecom.entity;

import com.sami.plant_ecom.enums.RoleName;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Data
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String profileImage;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Address> addresses;
    private String email;
    private String name;
    private String password;
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private RoleName role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Order> orders;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Review> reviews;
}
