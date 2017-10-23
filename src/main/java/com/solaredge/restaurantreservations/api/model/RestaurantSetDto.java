package com.solaredge.restaurantreservations.api.model;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.HashSet;
import java.util.Set;

@Value
@AllArgsConstructor
public class RestaurantSetDto {
    Set<RestaurantDto> restaurants;

    public RestaurantSetDto() {
        this(new HashSet<>());
    }
}
