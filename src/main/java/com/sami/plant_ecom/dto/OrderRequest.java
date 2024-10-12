package com.sami.plant_ecom.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private List<OrderItemRequest> items;
    private Long addressId;
    private String shippingType;
    private double totalAmount;  

    @Data
    public static class OrderItemRequest {
        private Long plantId;
        private int quantity;
    }
}
