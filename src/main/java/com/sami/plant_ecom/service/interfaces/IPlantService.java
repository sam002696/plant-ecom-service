package com.sami.plant_ecom.service.interfaces;

import com.sami.plant_ecom.dto.PlantRequest;
import com.sami.plant_ecom.entity.Plant;
import com.sami.plant_ecom.responses.PlantResponse;

import java.util.Map;

public interface IPlantService {
    Plant addPlant(PlantRequest plantRequest);

    PlantResponse getPlantById(Long plantId);

    Map<String, Object> search(Integer page, Integer size, String sortBy, String search);

    PlantResponse getPlantsByCategory(String cateogory);
}
