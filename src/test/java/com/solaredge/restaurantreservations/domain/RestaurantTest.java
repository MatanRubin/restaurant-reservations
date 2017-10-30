package com.solaredge.restaurantreservations.domain;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class RestaurantTest {

    private Restaurant restaurant;

    @Before
    public void setUp() throws Exception {
        restaurant = new Restaurant(1L, "Moses", "Shenkar");
        restaurant.addTable(new Table(1L, "Round Table", 4));
    }

    @Test
    public void addTable() throws Exception {
        // given
        int nTables = restaurant.getTables().size();
        Table table = new Table(2L, "Square Table", 8);

        // when
        restaurant.addTable(table);

        // then
        assertEquals(nTables + 1, restaurant.getTables().size());
        assertEquals(table, restaurant.getTables().get(2L));
    }

    @Test
    public void removeTable() throws Exception {
        // given
        assertEquals(1, restaurant.getTables().size());

        // when
        restaurant.removeTable(restaurant.getTables().values().iterator().next().getId());

        // then
        assertEquals(0, restaurant.getTables().size());
    }
}
