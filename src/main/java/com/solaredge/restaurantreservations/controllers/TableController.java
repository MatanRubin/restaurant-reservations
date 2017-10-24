package com.solaredge.restaurantreservations.controllers;

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
        return restaurantService.addTableToRestaurant(restaurantId, tableDto);
    }

    @GetMapping("/restaurants/{restaurantId}/tables/{tableId}")
    @ResponseStatus(HttpStatus.OK)
    TableDto getTable(@PathVariable Long  restaurantId, @PathVariable Long tableId) {
        return tableService.getTableById(tableId);
    }

    @DeleteMapping("/restaurants/{restaurantId}/tables/{tableId}")
    @ResponseStatus(HttpStatus.OK)
    void deleteTable(@PathVariable Long  restaurantId, @PathVariable Long tableId) {
        restaurantService.removeTableFromRestaurant(restaurantId, tableId);
        tableService.deleteTableById(tableId);
    }
}
