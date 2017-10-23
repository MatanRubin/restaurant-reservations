package com.solaredge.restaurantreservations.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TableDto {
    private Long id;
    private String name;
    private int capacity;

    public TableDto() {
        this(null, null, 0);
    }
}
