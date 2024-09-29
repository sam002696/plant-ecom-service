package com.sami.plant_ecom.service.impl;

import com.sami.plant_ecom.dto.PlantRequest;
import com.sami.plant_ecom.entity.Plant;
import com.sami.plant_ecom.exceptions.CustomMessageException;
import com.sami.plant_ecom.repository.PlantRepository;
import com.sami.plant_ecom.responses.PlantResponse;
import com.sami.plant_ecom.service.interfaces.IPlantService;
import com.sami.plant_ecom.utils.ServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PlantService implements IPlantService {

    @Autowired
    private PlantRepository plantRepository;

    @Override
    public Plant addPlant(PlantRequest plantRequest) {
        Plant plant = new Plant();
        plant.setPlantName(plantRequest.getPlantName());
        plant.setPlantDesc(plantRequest.getPlantDesc());
        plant.setPlantImageUrl(plantRequest.getPlantImageUrl());
        plant.setSold(plantRequest.getSold());
        plant.setRating(plantRequest.getRating());
        plant.setQuantity(plantRequest.getQuantity());
        plant.setPrice(plantRequest.getPrice());
        plant.setFavorite(plantRequest.isFavorite());
        plant.setCategory(plantRequest.getCategory());

        return plantRepository.save(plant);

    }

    @Override
    public PlantResponse getPlantById(Long plantId) {
        Plant plant = plantRepository.findById(plantId)
                .orElseThrow(() -> new CustomMessageException("Plant not found!"));

        return PlantResponse.select(plant);

    }

    @Override
    public Map<String, Object> search(Integer page, Integer size, String sortBy, String search) {
        ServiceHelper<Plant> serviceHelper = new ServiceHelper<>(Plant.class);
        return serviceHelper.getList(
                plantRepository.search(search, serviceHelper.getPageable(sortBy, page, size)),
                page, size);
    }

    @Override
    public List<PlantResponse> getPlantsByCategory(String category) {
        List<Plant> plants = plantRepository.findByCategory(category);

        // Map each Plant entity to a PlantResponse DTO
        return plants.stream()
                .map(PlantResponse::select)
                .collect(Collectors.toList());
    }



    @Override
    public Plant updatePlant(Long plantId, PlantRequest plantRequest) {
        // Fetch the existing plant
        Plant existingPlant = plantRepository.findById(plantId)
                .orElseThrow(() -> new RuntimeException("Plant not found"));

        // Update the plant details from the request
        existingPlant.setPlantName(plantRequest.getPlantName());
        existingPlant.setSold(plantRequest.getSold());
        existingPlant.setRating(plantRequest.getRating());
        existingPlant.setPrice(plantRequest.getPrice());
        existingPlant.setPlantDesc(plantRequest.getPlantDesc());
        existingPlant.setQuantity(plantRequest.getQuantity());
        existingPlant.setFavorite(plantRequest.isFavorite());
        existingPlant.setCategory(plantRequest.getCategory());
        existingPlant.setPlantImageUrl(plantRequest.getPlantImageUrl());

        // Save the updated plant entity
        return plantRepository.save(existingPlant);
    }

}
