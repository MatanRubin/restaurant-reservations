package com.solaredge.restaurantreservations.services;

import com.solaredge.restaurantreservations.api.model.TableDto;

import java.util.Set;

public interface TableService {
    TableDto getTableById(Long id);
    Set<TableDto> getAllTables();
    TableDto createTable(TableDto table);
    void deleteTableById(Long id);
}
