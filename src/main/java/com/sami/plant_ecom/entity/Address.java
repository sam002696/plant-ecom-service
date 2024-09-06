package com.sami.plant_ecom.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "addresses") // Changed table name to 'addresses' to better reflect the entity.
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String streetAddress;
    private String addressLine2;
    private String city;
    private String state;
    private String zipcode;
    private String country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")  // Define foreign key column for the relationship.
    private User user;
}
