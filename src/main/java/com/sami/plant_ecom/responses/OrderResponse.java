package com.sami.plant_ecom.responses;

import com.sami.plant_ecom.entity.Order;
import com.sami.plant_ecom.entity.OrderItem;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderResponse {
    private Long orderId;
    private UserResponse user;
    private double total;
    private String orderStatus;
    private String shippingType;  // New field for shipping type
    private AddressResponse address;  // Include AddressResponse
    private List<OrderItemResponse> orderItems;

    public static OrderResponse selectOrder(Order order) {
        OrderResponse response = new OrderResponse();
        response.setOrderId(order.getOrderId());
        response.setUser(UserResponse.selectUser(order.getUser()));
        response.setTotal(order.getTotal());
        response.setOrderStatus(order.getOrderStatus().name());

        response.setShippingType(order.getShippingType());

        // Set address using the AddressResponse conversion
        response.setAddress(AddressResponse.select(order.getAddress()));

        response.setOrderItems(order.getOrderItems().stream().map(OrderItemResponse::selectOrderItem)
                .collect(Collectors.toList()));
        return response;
    }

    @Data
    public static class OrderItemResponse {
        private String plantName;
        private int quantity;
        private double price;
        private String plantImageUrl;

        public static OrderItemResponse selectOrderItem(OrderItem orderItem) {
            OrderItemResponse response = new OrderItemResponse();
            response.setPlantImageUrl(orderItem.getPlant().getPlantImageUrl());
            response.setPlantName(orderItem.getPlant().getPlantName());
            response.setQuantity(orderItem.getQuantity());
            response.setPrice(orderItem.getPrice());
            return response;
        }
    }
}
