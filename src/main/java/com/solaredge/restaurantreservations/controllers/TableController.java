package com.solaredge.restaurantreservations.controllers;

import com.solaredge.restaurantreservations.api.model.RestaurantDto;
import com.solaredge.restaurantreservations.api.model.TableDto;
import com.solaredge.restaurantreservations.api.model.TableSetDto;
import com.solaredge.restaurantreservations.mappers.RestaurantMapper;
import com.solaredge.restaurantreservations.services.RestaurantService;
import com.solaredge.restaurantreservations.services.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TableController {

    private final TableService tableService;
    private final RestaurantService restaurantService;
    private final RestaurantMapper restaurantMapper = RestaurantMapper.INSTANCE;

    @Autowired
    public TableController(TableService tableService, RestaurantService restaurantService) {
        this.tableService = tableService;
        this.restaurantService = restaurantService;
    }

    @GetMapping("/restaurants/{restaurantId}/tables")
    @ResponseStatus(HttpStatus.OK)
    TableSetDto getAllTables(@PathVariable Long restaurantId) {
        return new TableSetDto(restaurantService.getRestaurantById(restaurantId).getTableDtos());
    }

    @PostMapping("/restaurants/{restaurantId}/tables")
    @ResponseStatus(HttpStatus.CREATED)
    TableDto createTable(@PathVariable Long restaurantId, @RequestBody TableDto tableDto) {
        RestaurantDto restaurantDto = restaurantService.addTableToRestaurant(restaurantId, tableDto);
        // TODO do services depend on each other? does adding a table to a restaurant return a table or a restaurant?
        return null;
    }
}
