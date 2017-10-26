package com.solaredge.restaurantreservations.services;

import com.solaredge.restaurantreservations.api.model.ReservationDto;
import com.solaredge.restaurantreservations.api.model.RestaurantDto;
import com.solaredge.restaurantreservations.api.model.TableDto;

import java.time.LocalDateTime;
import java.util.Set;

public interface RestaurantService {
    RestaurantDto createRestaurant(RestaurantDto restaurantDto);
    Set<RestaurantDto> findAllRestaurants();
    RestaurantDto getRestaurantById(Long id);
    Set<ReservationDto> getReservationsForRestaurant(Long restaurantId);
    void deleteRestaurant(Long id);
    TableDto addTableToRestaurant(Long restaurantId, TableDto tableDto);
    void removeTableFromRestaurant(Long restaurantId, Long tableId);
    Set<TableDto> findAvailableTables(Long restaurantId, LocalDateTime startTime, LocalDateTime endTime, int nPeople);
    void reserveTable(Long restaurantId, ReservationDto reservationDto);
    void cancelReservations(Long restaurantId, Long reservationId);
}
