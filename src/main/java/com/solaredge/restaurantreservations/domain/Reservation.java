package com.solaredge.restaurantreservations.domain;

import lombok.Value;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Value
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String tableName;
    private int nPeople;

    public Reservation() {
        this(null, null, null, null, null, 0);
    }

    public Reservation(
            Long id,
            String name,
            LocalDateTime startTime,
            LocalDateTime endTime,
            String tableName,
            int nPeople) {

        if (startTime != null && startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }

        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.tableName = tableName;
        this.nPeople = nPeople;
    }
}
