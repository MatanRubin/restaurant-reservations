package com.solaredge.restaurantreservations.api.model;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@AllArgsConstructor
public class ReservationDto {
    private Long id;
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String tableName;
    private int nPeople;

    public ReservationDto() {
        this(null, null, null, null, null, 0);
    }
}
