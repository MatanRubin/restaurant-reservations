package com.solaredge.restaurantreservations.domain;

import lombok.AllArgsConstructor;
import lombok.Synchronized;
import lombok.Value;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
    private Set<Reservation> reservations = new HashSet<>();

    @Transient
    private Map<Long, Reservation> reservationsById = new HashMap<>();

    @Transient
    private TreeMap<LocalDateTime, Reservation> reservationSortedByTime = new TreeMap<>();

    public Table() {
        this(null, null, 0);
    }

    @PostLoad
    private void initReservationMaps() {
        reservations.forEach(reservation -> reservationsById.put(reservation.getId(), reservation));
        reservations.forEach(reservation -> reservationSortedByTime.put(reservation.getStartTime(), reservation));
    }

    public void addReservation(Reservation reservation) {
        if (!name.equals(reservation.getTableName())) {
            throw new IllegalStateException("Table name mismatch. actualTableName=" + name +
                    " reservationTableName=" + reservation.getTableName());
        }
        if (capacity < reservation.getNPeople()) {
            throw new IllegalStateException("Table too small. capacity=" + capacity +
                    " nPeople=" + reservation.getNPeople());
        }
        if (!isAvailableAt(reservation.getStartTime(), reservation.getEndTime())) {
            throw new IllegalStateException("Table is not available at startTime=" + reservation.getStartTime() +
                    " endDate=" + reservation.getEndTime());
        }

        addReservationToAllPlaces(reservation);
    }

    private void addReservationToAllPlaces(Reservation reservation) {
        reservations.add(reservation);
        reservationsById.put(reservation.getId(), reservation);
        reservationSortedByTime.put(reservation.getStartTime(), reservation);
        verifyNoOverlappingReservations();
    }

    public void removeReservation(Long reservationId) {
        Reservation removed = reservationsById.remove(reservationId);
        reservationSortedByTime.remove(removed.getStartTime());
        reservations.remove(removed);
    }

    public boolean isAvailableAt(LocalDateTime startTime, LocalDateTime endTime) {
        Map.Entry<LocalDateTime, Reservation> earlierReservationEntry = reservationSortedByTime.floorEntry(startTime);
        if (earlierReservationEntry != null) {
            if (earlierReservationEntry.getValue().getEndTime().isAfter(startTime)) {
                return false;
            }
        }

        Map.Entry<LocalDateTime, Reservation> laterReservationEntry = reservationSortedByTime.ceilingEntry(startTime);
        if (laterReservationEntry != null) {
            if (laterReservationEntry.getValue().getStartTime().isBefore(endTime)) {
                return false;
            }
        }

        return true;
    }

    private void verifyNoOverlappingReservations() {
        // TODO implement
    }

}
