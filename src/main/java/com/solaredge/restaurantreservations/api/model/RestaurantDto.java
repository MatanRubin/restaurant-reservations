package com.solaredge.restaurantreservations.api.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.HashSet;
import java.util.Set;

@Value
@AllArgsConstructor
public class RestaurantDto {
    private Long id;
    private String name;
    private String address;
    private Set<TableDto> tableDtos = new HashSet<>();

    public RestaurantDto() {
        this(null, null, null);
    }
}
