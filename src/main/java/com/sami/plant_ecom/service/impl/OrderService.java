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

        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
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


            // Reduce the plant quantity
            plant.setQuantity(plant.getQuantity() - itemRequest.getQuantity());
            plantRepository.save(plant);

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



    @Override
    public OrderResponse cancelOrder(Long orderId) {
        // Find the order from database
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomMessageException("Order not found"));

        // Check if the order is already cancelled
        if (order.getOrderStatus() == OrderStatus.CANCELLED) {
            throw new CustomMessageException("Order is already cancelled");
        }

        // Update the order status to "CANCELLED"
        order.setOrderStatus(OrderStatus.CANCELLED);

        // Update the stock for each plant in the order
        for (OrderItem item : order.getOrderItems()) {
            Plant plant = item.getPlant();
            plant.setQuantity(plant.getQuantity() + item.getQuantity());
            plantRepository.save(plant);
        }

        // Save the updated order
        Order updatedOrder = orderRepository.save(order);

        // Return the updated order response
        return OrderResponse.selectOrder(updatedOrder);
    }


    @Override
    public List<OrderResponse> findOrderByStatus(OrderStatus orderStatus) {
        List<Order> orders = orderRepository.findByOrderStatus(orderStatus);
        return orders.stream()
                .map(OrderResponse::selectOrder)  // Convert Order to OrderResponse
                .collect(Collectors.toList());
    }

}
