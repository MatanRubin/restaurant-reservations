package com.solaredge.restaurantreservations.domain;

import lombok.AllArgsConstructor;
import lombok.Value;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@Value
@AllArgsConstructor
public class Reservation {

    private Long id;
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String tableName;
    private int nPeople;

    public Reservation() {
        this(null, null, null, null, null, 0);
    }
}
