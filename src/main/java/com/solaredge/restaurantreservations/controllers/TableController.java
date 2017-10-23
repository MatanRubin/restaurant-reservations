package com.solaredge.restaurantreservations.controllers;

import com.solaredge.restaurantreservations.api.model.RestaurantDto;
import com.solaredge.restaurantreservations.api.model.TableDto;
import com.solaredge.restaurantreservations.api.model.TableSetDto;
import com.solaredge.restaurantreservations.domain.Restaurant;
import com.solaredge.restaurantreservations.domain.Table;
import com.solaredge.restaurantreservations.services.RestaurantService;
import com.solaredge.restaurantreservations.services.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

@RestController
@RequestMapping("/api")
public class TableController {

    private final TableService tableService;
    private final RestaurantService restaurantService;

    @Autowired
    public TableController(TableService tableService, RestaurantService restaurantService) {
        this.tableService = tableService;
        this.restaurantService = restaurantService;
    }

    // TODO implement
    @GetMapping("/restaurants/{restaurantId}/tables")
    @ResponseStatus(HttpStatus.OK)
    TableSetDto getAllTables(@PathVariable Long restaurantId) {
        return null;
    }

    // TODO implement
    @PostMapping("/restaurants/{restaurantId}/tables")
    @ResponseStatus(HttpStatus.CREATED)
    TableDto createTable(@PathVariable Long restaurantId, @RequestBody TableDto tableDto) {
        RestaurantDto restaurant = restaurantService.getRestaurantById(restaurantId);
        Restaurant updatedRestaurant = new Restaurant(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getAddress(),
                new HashSet<>() // TODO fix this
        );
        return null;
    }
}
