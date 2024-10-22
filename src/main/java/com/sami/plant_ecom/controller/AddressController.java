package com.sami.plant_ecom.controller;


import com.sami.plant_ecom.dto.AddressRequest;
import com.sami.plant_ecom.dto.PlantRequest;
import com.sami.plant_ecom.entity.Address;
import com.sami.plant_ecom.entity.Plant;
import com.sami.plant_ecom.enums.OrderStatus;
import com.sami.plant_ecom.helpers.CommonDataHelper;
import com.sami.plant_ecom.responses.AddressResponse;
import com.sami.plant_ecom.responses.OrderResponse;
import com.sami.plant_ecom.service.interfaces.IAddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sami.plant_ecom.utils.ResponseBuilder.success;
import static org.springframework.http.ResponseEntity.ok;


@RestController
@Tag(name = "Address API")
@RequestMapping("/api/v1/address")
public class AddressController {

    @Autowired
    private IAddressService addressService;

    @Autowired
    private CommonDataHelper commonDataHelper;


    @PostMapping("/add")
    @Operation(summary = "Add an address", responses = {
            @ApiResponse(description = "Successfully added an address",
                    responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AddressRequest.class)))
    })
    public ResponseEntity<JSONObject> addNewAddress(
            @RequestBody AddressRequest addressRequest
    ) {


        Address address = addressService.addAddress(addressRequest);

        return ok(success(address, "Address added successfully").getJson());
    }


    @GetMapping("/all")
    @Operation(summary = "Get all addresses for the logged-in user", responses = {
            @ApiResponse(description = "Successfully retrieved addresses",
                    responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AddressResponse.class)))
    })
    public ResponseEntity<JSONObject> getAllAddresses() {
        List<AddressResponse> addresses = addressService.getAllAddresses();
        return ok(success(addresses, "").getJson());
    }

}
