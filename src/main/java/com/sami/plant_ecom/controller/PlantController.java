package com.sami.plant_ecom.controller;


import com.sami.plant_ecom.dto.PlantRequest;
import com.sami.plant_ecom.entity.Plant;
import com.sami.plant_ecom.service.interfaces.IPlantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;

import static com.sami.plant_ecom.utils.ResponseBuilder.success;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@Tag(name = "Plant API")
@RequestMapping("/api/v1/plant")
public class PlantController {

    @Autowired
    private IPlantService plantService;


    @PostMapping("/add")
    @Operation(summary = "Add a plant", responses = {
            @ApiResponse(description = "Successfully added a plant",
                    responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PlantRequest.class)))
    })
    public ResponseEntity<JSONObject> addNewPlant(
            @RequestBody PlantRequest plantRequest
    ) {


        Plant plant = plantService.addPlant(plantRequest);

        return ok(success(plant, "Plant added successfully").getJson());
    }




}
