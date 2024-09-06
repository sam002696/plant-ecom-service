package com.sami.plant_ecom.entity;

import com.sami.plant_ecom.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private int quantity;
    private double total;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "plant_id")
    private Plant plant;


}
