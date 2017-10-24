package com.solaredge.restaurantreservations.domain;

import com.sun.javaws.exceptions.InvalidArgumentException;
import lombok.AllArgsConstructor;
import lombok.Synchronized;
import lombok.Value;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Value
@AllArgsConstructor
public class Table {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int capacity;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private TreeMap<LocalDateTime, Reservation> reservations = new TreeMap<>();

    public Table() {
        this(null, null, 0);
    }

    @Synchronized
    public void addReservation(Reservation reservation) {
        if (isAvailableAt(reservation.getStartDate(), reservation.getEndDate())) {
            reservations.put(reservation.getStartDate(), reservation);
        } else {
            throw new IllegalStateException("Table is not available at startDate=" + reservation.getStartDate() +
                    " endDate=" + reservation.getEndDate());
        }

        verifyNoOverlappingReservations();
    }

    @Synchronized
    void removeReservation(Reservation reservation) {
        reservations.remove(reservation.getStartDate());
    }

    private boolean isAvailableAt(LocalDateTime startTime, LocalDateTime endTime) {
        Reservation earlierReservation = reservations.floorEntry(startTime).getValue(); // XXX possible NPE
        if (earlierReservation != null && earlierReservation.getEndDate().isAfter(startTime)) {
            return false;
        }

        Reservation laterReservation = reservations.ceilingEntry(startTime).getValue(); // XXX possible NPE
        if (laterReservation != null && laterReservation.getStartDate().isBefore(endTime)) {
            return false;
        }

        return true;
    }

    private void verifyNoOverlappingReservations() {
        // TODO implement
    }
}
