package com.solaredge.restaurantreservations.domain;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class TableTest {
    private Table table;

    private static final Long ID = 1L;
    private static final String TABLE_NAME = "Round Table";
    private static final int CAPACITY = 4;
    private static final LocalDateTime now = LocalDateTime.now();

    @Before
    public void setUp() throws Exception {
        table = new Table(ID, TABLE_NAME, CAPACITY);
        table.addReservation(new Reservation(1L, "Rubin", now, now.plusHours(2L), TABLE_NAME, 2));
    }

    @Test
    public void addReservation() throws Exception {
        // given
        LocalDateTime now = LocalDateTime.now();
        Reservation reservation = new Reservation(
                2L,
                "Rubin",
                now.plusHours(5L),
                now.plusHours(6L),
                TABLE_NAME,
                2);
        int nReservations = table.getReservationsById().size();

        // when
        table.addReservation(reservation);

        // then
        assertEquals(nReservations + 1, table.getReservationsById().size());
    }

    @Test(expected = IllegalStateException.class)
    public void addReservationWithStartTimeOnAnotherReservation() throws Exception {
        // when
        table.addReservation(new Reservation(
                2L,
                "Maloni",
                now.plusHours(1L),
                now.plusHours(2L),
                "Round Table",
                3));
    }

    @Test(expected = IllegalStateException.class)
    public void addReservationWithEndTimeOnAnotherReservation() throws Exception {
        // when
        table.addReservation(new Reservation(
                2L,
                "Maloni",
                now.minusHours(1L),
                now.plusHours(1L),
                "Round Table",
                3));
    }

    @Test(expected = IllegalStateException.class)
    public void addReservationContainedInAnotherReservation() throws Exception {
        // when
        table.addReservation(new Reservation(
                2L,
                "Maloni",
                now.plusMinutes(30L),
                now.plusHours(1L),
                "Round Table",
                3));
    }

    @Test(expected = IllegalStateException.class)
    public void addReservationWithContainingAnotherReservation() throws Exception {
        // when
        table.addReservation(new Reservation(
                2L,
                "Maloni",
                now.minusHours(1L),
                now.plusHours(3L),
                "Round Table",
                3));
    }

    @Test
    public void removeReservation() throws Exception {
        //given
        int nReservations = table.getReservationsById().size();

        // when
        table.removeReservation(table.getReservationsById().values().iterator().next().getId());

        // then
        assertEquals(nReservations - 1, table.getReservationsById().size());
    }

}
