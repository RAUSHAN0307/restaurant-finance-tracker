package com.intellect.financeTracker.service.implementation;

import com.intellect.financeTracker.service.*;

import com.intellect.financeTracker.model.Restaurant;
import com.intellect.financeTracker.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImplementation implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public Restaurant addRestaurant(Long userId, Restaurant restaurant) {
        restaurant.setUserId(userId);
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long userId, Long restaurantId, Restaurant restaurant) {
        Optional<Restaurant> existingRestaurant = restaurantRepository.findById(restaurantId);
        if (existingRestaurant.isPresent() && existingRestaurant.get().getUserId().equals(userId)) {
            Restaurant updatedRestaurant = existingRestaurant.get();
            updatedRestaurant.setRestaurantName(restaurant.getRestaurantName());
            updatedRestaurant.setGstNumber(restaurant.getGstNumber());
            updatedRestaurant.setAddress(restaurant.getAddress());
            return restaurantRepository.save(updatedRestaurant);
        }
        return null;
    }

    @Override
    public void deleteRestaurant(Long userId, Long restaurantId) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        if (restaurant.isPresent() && restaurant.get().getUserId().equals(userId)) {
            restaurantRepository.deleteById(restaurantId);
        }
    }

    @Override
    public Restaurant getRestaurantById(Long userId, Long restaurantId) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        if (restaurant.isPresent() && restaurant.get().getUserId().equals(userId)) {
            return restaurant.get();
        }
        return null;
    }

    @Override
    public List<Restaurant> getAllRestaurants(Long userId) {
        return restaurantRepository.findByUserId(userId);
    }
}
