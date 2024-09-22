package com.sami.plant_ecom.service.interfaces;

import com.sami.plant_ecom.dto.OrderRequest;
import com.sami.plant_ecom.enums.OrderStatus;
import com.sami.plant_ecom.responses.OrderResponse;

import java.util.List;

public interface IOrderService {
    OrderResponse placeOrder(OrderRequest orderRequest);

    OrderResponse cancelOrder(Long orderId);

    List<OrderResponse> findOrderByStatus(OrderStatus orderStatus);
}
