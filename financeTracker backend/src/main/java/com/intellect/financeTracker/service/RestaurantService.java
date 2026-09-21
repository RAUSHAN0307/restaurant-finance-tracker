package com.intellect.financeTracker.service;

import com.intellect.financeTracker.model.Restaurant;
import java.util.List;

public interface RestaurantService {

    Restaurant addRestaurant(Long userId, Restaurant restaurant);

    Restaurant updateRestaurant(Long userId, Long restaurantId, Restaurant restaurant);

    void deleteRestaurant(Long userId, Long restaurantId);

    Restaurant getRestaurantById(Long userId, Long restaurantId);

    List<Restaurant> getAllRestaurants(Long userId);
}
