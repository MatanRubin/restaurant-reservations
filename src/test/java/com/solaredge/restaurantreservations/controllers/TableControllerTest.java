package com.solaredge.restaurantreservations.controllers;

import com.solaredge.restaurantreservations.api.model.RestaurantDto;
import com.solaredge.restaurantreservations.api.model.TableDto;
import com.solaredge.restaurantreservations.services.RestaurantService;
import com.solaredge.restaurantreservations.services.TableService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.HashSet;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TableControllerTest {
    @Mock
    TableService tableService;

    @Mock
    RestaurantService restaurantService;

    @InjectMocks
    TableController tableController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(tableController).build();
    }

    @Test
    public void getAllTables() throws Exception {
        // given
        RestaurantDto restaurant = new RestaurantDto(
                null,
                null,
                null,
                new HashSet<>(Arrays.asList(
                        new TableDto(1L, "VIP", 4),
                        new TableDto(2L, "VIP", 4),
                        new TableDto(3L, "VIP", 4)

                )));
        when(restaurantService.getRestaurantById(anyLong())).thenReturn(restaurant);

        // when + then
        mockMvc.perform(get("/api/restaurants/1/tables"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tables", hasSize(3)));
    }

    @Test
    public void createTable() throws Exception {
        // given

        // when

        // then
    }

}
