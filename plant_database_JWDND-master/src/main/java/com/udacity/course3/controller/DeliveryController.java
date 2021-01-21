package com.udacity.course3.controller;

import com.udacity.course3.entity.RecipeAndPrice;
import com.udacity.course3.entity.delivery.Delivery;
import com.udacity.course3.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/delivery")
public class DeliveryController {
    @Autowired
    DeliveryService deliveryService;

    @PostMapping
    public Long scheduleDelivery(@RequestBody Delivery delivery){
        return deliveryService.save(delivery);
    }

    @GetMapping("/bill/{deliveryId}")
    public RecipeAndPrice getBill(@PathVariable Long deliveryId){
        return deliveryService.getBill(deliveryId);
    }
}
