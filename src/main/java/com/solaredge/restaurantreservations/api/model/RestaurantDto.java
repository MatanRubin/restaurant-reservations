package com.solaredge.restaurantreservations.api.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Value
@AllArgsConstructor
public class RestaurantDto {
    private Long id;
    private String name;
    private String address;
    private Set<TableDto> tableDtos;

    public RestaurantDto() {
        this(null, null, null, Collections.emptySet());
    }

    public RestaurantDto(Long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.tableDtos = Collections.emptySet();
    }
}
