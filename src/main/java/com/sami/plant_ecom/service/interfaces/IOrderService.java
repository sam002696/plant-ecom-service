package com.sami.plant_ecom.service.interfaces;

import com.sami.plant_ecom.dto.OrderRequest;
import com.sami.plant_ecom.responses.OrderResponse;

public interface IOrderService {
    OrderResponse placeOrder(OrderRequest orderRequest);
}
