package com.sami.plant_ecom.repository;

import com.sami.plant_ecom.entity.Order;
import com.sami.plant_ecom.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository <Order, Long> {
    List<Order> findByOrderStatus(OrderStatus orderStatus);
}
