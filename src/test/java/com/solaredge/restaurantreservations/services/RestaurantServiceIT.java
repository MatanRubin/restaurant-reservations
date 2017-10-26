package com.solaredge.restaurantreservations.services;

import com.solaredge.restaurantreservations.api.model.ReservationDto;
import com.solaredge.restaurantreservations.api.model.RestaurantDto;
import com.solaredge.restaurantreservations.api.model.TableDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestaurantServiceIT {

    @Autowired
    RestaurantService restaurantService;

    private static final Long RESTAURANT_ID = 1L;
    private static final Long TABLE_ID = 1L;

    @Before
    public void setUp() throws Exception {
        restaurantService.createRestaurant(new RestaurantDto(RESTAURANT_ID, "Moses", "Shenkar St."));
        restaurantService.addTableToRestaurant(RESTAURANT_ID, new TableDto(TABLE_ID, "Round Table", 4));
    }

    @Test
    public void testReserveTable() throws Exception {
        // given

        // when
        Set<TableDto> availableTables = restaurantService.findAvailableTables(
                1L,
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(2L),
                3);
        restaurantService.reserveTable(RESTAURANT_ID, new ReservationDto(
                null,
                "Rubin",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(2L),
                availableTables.iterator().next().getName(),
                3
        ));

        // then
    }
}
