package com.solaredge.restaurantreservations.controllers;

import com.solaredge.restaurantreservations.api.model.ReservationDto;
import com.solaredge.restaurantreservations.api.model.ReservationSetDto;
import com.solaredge.restaurantreservations.api.model.RestaurantDto;
import com.solaredge.restaurantreservations.exceptions.NotFoundException;
import com.solaredge.restaurantreservations.services.ReservationService;
import com.solaredge.restaurantreservations.services.RestaurantService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReservationControllerTest {

    private static final Long RESTAURANT_ID = 1L;
    private MockMvc mockMvc;

    @Mock
    private RestaurantService restaurantService;

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private ReservationController reservationController;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();
    }

    @Test
    public void getAllReservations() throws Exception {
        // given
        Set<ReservationDto> reservationDtos = new HashSet<>(Collections.singletonList(new ReservationDto()));
        when(restaurantService.getReservationsForRestaurant(anyLong())).thenReturn(reservationDtos);

        // when + then
        mockMvc.perform(get("/api/restaurants/1/reservations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reservations", hasSize(1)));
    }

    @Test
    public void getNonExistentReservation() throws Exception {
        // given
        when(reservationService.getReservationById(anyLong())).thenThrow(NotFoundException.class);

        // when + then
        mockMvc.perform(get("/api/restaurants/1/reservations/10"))
                .andExpect(status().isNotFound());
    }
}
