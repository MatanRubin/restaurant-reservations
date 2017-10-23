package com.solaredge.restaurantreservations.controllers;

import com.solaredge.restaurantreservations.api.model.RestaurantDto;
import com.solaredge.restaurantreservations.api.model.RestaurantSetDto;
import com.solaredge.restaurantreservations.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    RestaurantSetDto getAllRestaurants() {
        return new RestaurantSetDto(restaurantService.findAllRestaurants());
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    RestaurantDto getRestaurantById(@PathVariable Long id) {
        return restaurantService.getRestaurantById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    RestaurantDto createRestaurant(@RequestBody RestaurantDto restaurantDto) {
        return restaurantService.createRestaurant(restaurantDto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    void deleteRestaurantById(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
    }
}
