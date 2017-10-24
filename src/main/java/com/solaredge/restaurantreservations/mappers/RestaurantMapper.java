package com.solaredge.restaurantreservations.mappers;

import com.solaredge.restaurantreservations.api.model.RestaurantDto;
import com.solaredge.restaurantreservations.domain.Restaurant;

import java.util.Collections;
import java.util.HashSet;
import java.util.stream.Collectors;

public class RestaurantMapper {
    public static final RestaurantMapper INSTANCE = new RestaurantMapper();

    private final TableMapper tableMapper = TableMapper.INSTANCE;

    public Restaurant restaurantDtoToRestaurant(RestaurantDto restaurantDto) {
        if (restaurantDto == null) {
            return null;
        }

        return new Restaurant(
                restaurantDto.getId(),
                restaurantDto.getName(),
                restaurantDto.getAddress(),
                restaurantDto.getTableDtos().stream().map(tableMapper::tableDtoToTable).collect(Collectors.toSet())
        );
    }

    public RestaurantDto restaurantToRestaurantDto(Restaurant restaurant) {
        return restaurant == null ?
                null :
                new RestaurantDto(
                        restaurant.getId(),
                        restaurant.getName(),
                        restaurant.getAddress(),
                        restaurant.getTables().stream().map(tableMapper::tableToTableDto).collect(Collectors.toSet())
                );
    }
}
