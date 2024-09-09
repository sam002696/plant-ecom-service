package com.sami.plant_ecom.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "plants")
public class Plant extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String plantName;
    private double price;
    private double sold;
    private double rating;
//    private int reviews;
    private String plantDesc;
    private int quantity;
    private boolean favorite;
    private String category;

    @OneToMany(mappedBy = "plant", fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;
}
