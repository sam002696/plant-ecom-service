package com.sami.plant_ecom.service.impl;

import com.sami.plant_ecom.dto.OrderRequest;
import com.sami.plant_ecom.entity.Order;
import com.sami.plant_ecom.entity.OrderItem;
import com.sami.plant_ecom.entity.Plant;
import com.sami.plant_ecom.entity.User;
import com.sami.plant_ecom.enums.OrderStatus;
import com.sami.plant_ecom.exceptions.CustomMessageException;
import com.sami.plant_ecom.repository.OrderRepository;
import com.sami.plant_ecom.repository.PlantRepository;
import com.sami.plant_ecom.repository.UserRepository;
import com.sami.plant_ecom.responses.OrderResponse;
import com.sami.plant_ecom.security.UserPrincipal;
import com.sami.plant_ecom.service.interfaces.IOrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    @Autowired
    private  OrderRepository orderRepository;
    @Autowired
    private  PlantRepository plantRepository;
    @Autowired
    private  UserRepository userRepository;

    @Override
    public OrderResponse placeOrder(OrderRequest orderRequest) {

        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long loggedInUserId = userPrincipal.getId();

        User user = userRepository.findById(loggedInUserId)
                .orElseThrow(() -> new CustomMessageException("Logged-in user not found"));

        // Create a new Order entity
        Order order = new Order();
        order.setUser(user);
        order.setOrderStatus(OrderStatus.PLACED);

        // Create OrderItems based on the request
        List<OrderItem> orderItems = orderRequest.getItems().stream().map(itemRequest -> {
            Plant plant = plantRepository.findById(itemRequest.getPlantId())
                    .orElseThrow(() -> new CustomMessageException("Plant not found"));


            if (itemRequest.getQuantity() > plant.getQuantity()) {
                throw new CustomMessageException("Requested quantity for plant " +  plant.getPlantName().toLowerCase() +
                        " exceeds available stock.");
            }


            OrderItem orderItem = new OrderItem();
            orderItem.setPlant(plant);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setPrice(plant.getPrice() * itemRequest.getQuantity());
            orderItem.setOrder(order);
            return orderItem;
        }).collect(Collectors.toList());

        // Calculating total
        double total = orderItems.stream().mapToDouble(OrderItem::getPrice).sum();
        order.setTotal(total);
        order.setOrderItems(orderItems);

        // Saving the order
        Order savedOrder = orderRepository.save(order);

        // Returning the response
        return OrderResponse.selectOrder(savedOrder);
    }

}
