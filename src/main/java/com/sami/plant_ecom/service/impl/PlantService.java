package com.sami.plant_ecom.service.impl;

import com.sami.plant_ecom.dto.PlantRequest;
import com.sami.plant_ecom.entity.Plant;
import com.sami.plant_ecom.repository.PlantRepository;
import com.sami.plant_ecom.service.interfaces.IPlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlantService implements IPlantService {

    @Autowired
    private PlantRepository plantRepository;

    @Override
    public Plant addPlant(PlantRequest plantRequest) {
        Plant plant = new Plant();
        plant.setPlantName(plantRequest.getPlantName());
        plant.setSold(plantRequest.getSold());
        plant.setRating(plantRequest.getRating());
        plant.setQuantity(plantRequest.getQuantity());
        plant.setFavorite(plantRequest.isFavorite());
        plant.setCategory(plantRequest.getCategory());

        return plantRepository.save(plant);

    }
}
