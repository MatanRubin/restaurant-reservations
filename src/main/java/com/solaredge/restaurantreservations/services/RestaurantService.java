package com.solaredge.restaurantreservations.services;

import com.solaredge.restaurantreservations.api.model.RestaurantDto;

import java.util.Set;

public interface RestaurantService {
    RestaurantDto createRestaurant(RestaurantDto restaurantDto);
    Set<RestaurantDto> findAllRestaurants();
    RestaurantDto getRestaurantById(Long id);
    void deleteRestaurant(Long id);
}
