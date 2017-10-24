package com.solaredge.restaurantreservations.services;

import com.solaredge.restaurantreservations.api.model.RestaurantDto;
import com.solaredge.restaurantreservations.domain.Restaurant;
import com.solaredge.restaurantreservations.mappers.RestaurantMapper;
import com.solaredge.restaurantreservations.repositories.RestaurantRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class RestaurantServiceImplTest {
    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private TableService tableService;

    private final RestaurantMapper mapper = RestaurantMapper.INSTANCE;
    private RestaurantService restaurantService;

    private static final Long ID = 1L;
    private static final String NAME = "Moses";
    private static final String ADDRESS = "Washington St.";

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        restaurantService = new RestaurantServiceImpl(restaurantRepository, tableService);
    }

    @Test
    public void createRestaurant() throws Exception {
        // given
        RestaurantDto restaurantDto = new RestaurantDto(null, NAME, ADDRESS);
        Restaurant savedRestaurant = new Restaurant(ID, NAME, ADDRESS, new HashSet<>());
        when(restaurantRepository.save(any())).thenReturn(savedRestaurant);

        // when
        RestaurantDto savedRestaurantDto = restaurantService.createRestaurant(restaurantDto);

        // then
        assertEquals(savedRestaurantDto.getAddress(), restaurantDto.getAddress());
        assertEquals(savedRestaurantDto.getName(), restaurantDto.getName());
        assertEquals(savedRestaurantDto.getId(), ID);
    }

    @Test
    public void findAllRestaurants() throws Exception {
        // given
        List<Restaurant> restaurants = Arrays.asList(
                new Restaurant(1L, NAME, ADDRESS, new HashSet<>()),
                new Restaurant(2L, NAME, ADDRESS, new HashSet<>()),
                new Restaurant(3L, NAME, ADDRESS, new HashSet<>())
        );
        when(restaurantRepository.findAll()).thenReturn(restaurants);

        // when
        Set<RestaurantDto> allRestaurants = restaurantService.findAllRestaurants();

        // then
        assertEquals(restaurants.size(), allRestaurants.size());
    }

    @Test
    public void getRestaurantById() throws Exception {
        // given

        // when

        // then
    }

    @Test
    public void getNonExistingById() throws Exception {
        // given

        // when

        // then
    }

    @Test
    public void deleteRestaurant() throws Exception {
        // given

        // when

        // then
    }

    @Test
    public void deleteNonExistingRestaurant() throws Exception {

    }

}
