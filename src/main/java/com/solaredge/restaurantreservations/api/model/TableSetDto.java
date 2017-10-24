package com.solaredge.restaurantreservations.api.model;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.Collections;
import java.util.Set;

@Value
@AllArgsConstructor
public class TableSetDto {
    Set<TableDto> tables;

    public TableSetDto() {
        this(Collections.emptySet());
    }
}
