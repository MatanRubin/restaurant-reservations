package com.solaredge.restaurantreservations.mappers;

import com.solaredge.restaurantreservations.api.model.RestaurantDto;
import com.solaredge.restaurantreservations.domain.Restaurant;

import java.util.Collections;
import java.util.HashSet;

public class RestaurantMapper {
    public static final RestaurantMapper INSTANCE = new RestaurantMapper();

    public Restaurant restaurantDtoToRestaurant(RestaurantDto restaurantDto) {
        if (restaurantDto == null) {
            return null;
        }

        return new Restaurant(
                restaurantDto.getId(),
                restaurantDto.getName(),
                restaurantDto.getAddress(),
                new HashSet<>() // FIXME
        );
    }

    public RestaurantDto restaurantToRestaurantDto(Restaurant restaurant) {
        return restaurant == null ?
                null :
                new RestaurantDto(
                        restaurant.getId(),
                        restaurant.getName(),
                        restaurant.getAddress(),
                        Collections.emptySet()
                );
    }
}
