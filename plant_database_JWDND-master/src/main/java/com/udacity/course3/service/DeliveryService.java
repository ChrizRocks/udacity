package com.udacity.course3.service;

import com.udacity.course3.entity.RecipeAndPrice;
import com.udacity.course3.entity.delivery.Delivery;
import com.udacity.course3.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {

    @Autowired
    DeliveryRepository deliveryRepository;

    public RecipeAndPrice getBill(Long deliveryId){
        return deliveryRepository.getBill(deliveryId);
    }
    public Long save(Delivery delivery){
        delivery.getPlants().forEach(plant -> plant.setDelivery(delivery));
        deliveryRepository.persist(delivery);
        return delivery.getId();
    }

}
