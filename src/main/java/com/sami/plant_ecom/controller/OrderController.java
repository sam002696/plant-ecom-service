package com.sami.plant_ecom.controller;


import com.sami.plant_ecom.dto.OrderRequest;
import com.sami.plant_ecom.dto.PlantRequest;
import com.sami.plant_ecom.entity.Plant;
import com.sami.plant_ecom.responses.OrderResponse;
import com.sami.plant_ecom.service.interfaces.IOrderService;
import com.sami.plant_ecom.service.interfaces.IPlantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.sami.plant_ecom.utils.ResponseBuilder.success;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@Tag(name = "Order API")
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;



    @PostMapping("/place")
    @Operation(summary = "Place an order", responses = {
            @ApiResponse(description = "Successfully added an order",
                    responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderRequest.class)))
    })
    public ResponseEntity<JSONObject> placeNewOrder(
            @RequestBody OrderRequest orderRequest
    ) {


        OrderResponse orderResponse = orderService.placeOrder(orderRequest);

        return ok(success(orderResponse, "Order has been placed successfully").getJson());
    }

}
