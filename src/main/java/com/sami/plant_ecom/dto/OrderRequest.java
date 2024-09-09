package com.sami.plant_ecom.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private List<OrderItemRequest> items;

    @Data
    public static class OrderItemRequest {
        private Long plantId;
        private int quantity;
    }
}
