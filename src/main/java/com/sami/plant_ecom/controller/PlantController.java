package com.sami.plant_ecom.controller;


import com.sami.plant_ecom.dto.PlantRequest;
import com.sami.plant_ecom.entity.Plant;
import com.sami.plant_ecom.helpers.CommonDataHelper;
import com.sami.plant_ecom.responses.PlantResponse;
import com.sami.plant_ecom.service.interfaces.IPlantService;
import com.sami.plant_ecom.utils.PaginatedResponse;
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
import java.util.Map;

import static com.sami.plant_ecom.utils.ResponseBuilder.paginatedSuccess;
import static com.sami.plant_ecom.utils.ResponseBuilder.success;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@Tag(name = "Plant API")
@RequestMapping("/api/v1/plant")
public class PlantController {

    @Autowired
    private IPlantService plantService;

    @Autowired
    private CommonDataHelper commonDataHelper;


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



    @GetMapping("{plantId}")
    @Operation(summary = "Get a plant", description = "Get a plant by it's id")
    @ApiResponse(responseCode = "200", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = PlantResponse.class))
    })
    public ResponseEntity<JSONObject> getPlantById(@PathVariable Long plantId) {

        PlantResponse plantResponse = plantService.getPlantById(plantId);

        return ok(success(plantResponse).getJson());

    }


    @GetMapping("/list")
    @Operation(summary = "show lists of all plants", description = "show lists of all plants")
    @ApiResponse(responseCode = "200", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = PlantResponse.class))
    })
    public ResponseEntity<JSONObject> lists(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                            @RequestParam(value = "size", defaultValue = "10") Integer size,
                                            @RequestParam(value = "sortBy", defaultValue = "") String sortBy,
                                            @RequestParam(value = "search", defaultValue = "") String search
    ) {

        PaginatedResponse response = new PaginatedResponse();
        Map<String, Object> map = plantService.search(page, size, sortBy, search);
        List<Plant> postList = (List<Plant>) map.get("lists");
        List<PlantResponse> responses = postList.stream().map(PlantResponse::select).toList();
        commonDataHelper.getCommonData(page, size, map, response, responses);
        return ok(paginatedSuccess(response).getJson());
    }


}
