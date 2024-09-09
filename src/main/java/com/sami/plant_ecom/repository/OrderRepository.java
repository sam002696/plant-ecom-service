package com.sami.plant_ecom.repository;

import com.sami.plant_ecom.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository <Order, Long> {
}
