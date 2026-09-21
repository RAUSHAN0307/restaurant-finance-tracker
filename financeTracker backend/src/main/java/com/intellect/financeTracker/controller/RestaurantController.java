package com.intellect.financeTracker.controller;

import com.intellect.financeTracker.model.Restaurant;
import com.intellect.financeTracker.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/restaurants")
@PreAuthorize("hasAnyRole('OWNER', 'MANAGER')")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping("/{userId}/add")
    public Restaurant addRestaurant(
            @PathVariable("userId") Long userId,
            @RequestBody Restaurant restaurant) {
        return restaurantService.addRestaurant(userId, restaurant);
    }

    @PutMapping("/{userId}/update/{restaurantId}")
    public Restaurant updateRestaurant(
            @PathVariable("userId") Long userId,
            @PathVariable("restaurantId") Long restaurantId,
            @RequestBody Restaurant restaurant) {
        return restaurantService.updateRestaurant(userId, restaurantId, restaurant);
    }

    @DeleteMapping("/{userId}/delete/{restaurantId}")
    public String deleteRestaurant(
            @PathVariable("userId") Long userId,
            @PathVariable("restaurantId") Long restaurantId) {
        restaurantService.deleteRestaurant(userId, restaurantId);
        return "Restaurant deleted successfully";
    }

    @GetMapping("/{userId}/get/{restaurantId}")
    public Restaurant getRestaurantById(
            @PathVariable("userId") Long userId,
            @PathVariable("restaurantId") Long restaurantId) {
        return restaurantService.getRestaurantById(userId, restaurantId);
    }

    @GetMapping("/{userId}/all")
    public List<Restaurant> getAllRestaurants(@PathVariable("userId") Long userId) {
        return restaurantService.getAllRestaurants(userId);
    }
}
