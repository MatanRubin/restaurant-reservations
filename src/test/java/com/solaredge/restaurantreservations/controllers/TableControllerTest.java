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
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
    public void getTable() throws Exception {
        // given
        TableDto tableDto = new TableDto(1L, "Kaka", 5);
        when(tableService.getTableById(anyLong())).thenReturn(tableDto);

        // when + then
        mockMvc.perform(get("/api/restaurants/1/tables/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("Kaka")))
                .andExpect(jsonPath("$.capacity", equalTo(5)));
    }

    @Test
    public void deleteTable() throws Exception {
        mockMvc.perform(delete("/api/restaurants/1/tables/1"))
                .andExpect(status().isOk());
    }
}
