package com.solaredge.restaurantreservations.controllers;

import com.solaredge.restaurantreservations.api.model.ReservationDto;
import com.solaredge.restaurantreservations.api.model.ReservationListDto;
import com.solaredge.restaurantreservations.api.model.RestaurantDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ReservationController {

    @GetMapping("/restaurants/{restaurantId}/tables/{tableId}/reservations")
    public ReservationListDto getAllReservationsForTable(@PathVariable Long restaurantId, @PathVariable Long tableId) {
        return null;
    }

    @GetMapping("/restaurants/{restaurantId}/tables/{tableId}/reservations/{reservationId}")
    public ReservationDto getReservationsInfo(
            @PathVariable Long restaurantId,
            @PathVariable Long tableId,
            @PathVariable Long reservationId) {
        return null;
    }

    @PostMapping("/restaurants/{restaurantId}/tables/{tableId}/reservations")
    public ReservationDto reserveTable(
            @PathVariable Long restaurantId,
            @PathVariable Long tableId,
            @RequestBody RestaurantDto restaurantDto) {
        return null;
    }

    @DeleteMapping("/restaurants/{restaurantId}/tables/{tableId}/reservations/{reservationId}")
    public void cancelReservation(
            @PathVariable Long restaurantId,
            @PathVariable Long tableId,
            @PathVariable Long reservationId) {
    }

    @PutMapping("/restaurants/{restaurantId}/tables/{tableId}/reservations")
    public ReservationListDto updateReservation(
            @PathVariable Long restaurantId,
            @PathVariable Long tableId,
            @RequestBody ReservationDto reservationDto) {
        return null;
    }
}
