package com.solaredge.restaurantreservations.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.Set;

@Getter
@AllArgsConstructor
public class ReservationSetDto {
    Set<ReservationDto> reservations;

    public ReservationSetDto() {
        this(Collections.emptySet());
    }
}
