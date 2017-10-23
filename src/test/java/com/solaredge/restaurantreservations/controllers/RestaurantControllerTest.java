package com.solaredge.restaurantreservations.controllers;

import com.solaredge.restaurantreservations.api.model.RestaurantDto;
import com.solaredge.restaurantreservations.services.RestaurantService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.HashSet;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RestaurantControllerTest {

    @Mock
    private RestaurantService restaurantService;

    @InjectMocks
    private RestaurantController restaurantController;

    private MockMvc mockMvc;


    @Before
    public void setUp() throws Exception {
        initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(restaurantController).build();
    }

    @Test
    public void getAllRestaurants() throws Exception {
        // given
        HashSet<RestaurantDto> restaurants = new HashSet<>(Arrays.asList(
                new RestaurantDto(1L, null, null),
                new RestaurantDto(2L, null, null),
                new RestaurantDto(3L, null, null)
        ));
        when(restaurantService.findAllRestaurants()).thenReturn(restaurants);

        // when + then
        mockMvc.perform(get("/api/restaurants").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.restaurants", hasSize(3)));
    }

    @Test
    public void getRestaurantById() throws Exception {
        // given
        RestaurantDto restaurant = new RestaurantDto(1L, null, null);
        when(restaurantService.getRestaurantById(eq(1L))).thenReturn(restaurant);

        // when + then
        mockMvc.perform(get("/api/restaurants/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)));
    }

    @Ignore // TODO implement exception throwing
    @Test
    public void getNonexistentRestaurantById() throws Exception {
        // when + then
        mockMvc.perform(get("/api/restaurants/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createRestaurant() throws Exception {
        // given
        RestaurantDto addedRestaurant = new RestaurantDto(1L, "Zozobra", "Shenkar St.");
        when(restaurantService.createRestaurant(any())).thenReturn(addedRestaurant);

        // when + then
        mockMvc.perform(post("/api/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.asJsonString(new RestaurantDto(null, "Zozobra", "Shenkar St."))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo(1)));
    }

    @Test
    public void deleteRestaurantById() throws Exception {
        // when + then
        mockMvc.perform(delete("/api/restaurants/1")).andExpect(status().isOk());
    }

}
