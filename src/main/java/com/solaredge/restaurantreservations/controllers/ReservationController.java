package com.solaredge.restaurantreservations.controllers;

import com.solaredge.restaurantreservations.api.model.ReservationDto;
import com.solaredge.restaurantreservations.api.model.ReservationSetDto;
import com.solaredge.restaurantreservations.api.model.TableSetDto;
import com.solaredge.restaurantreservations.services.ReservationService;
import com.solaredge.restaurantreservations.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class ReservationController {

    private final RestaurantService restaurantService;
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(RestaurantService restaurantService, ReservationService reservationService) {
        this.restaurantService = restaurantService;
        this.reservationService = reservationService;
    }

    @GetMapping("/restaurants/{restaurantId}/reservations")
    public ReservationSetDto getAllReservations(@PathVariable Long restaurantId) {
        return new ReservationSetDto(restaurantService.getReservationsForRestaurant(restaurantId));
    }

    @GetMapping("/restaurants/{restaurantId}/reservations/{reservationId}")
    public ReservationDto getReservationInfo(@PathVariable Long restaurantId, @PathVariable Long reservationId) {
        return reservationService.getReservationById(reservationId);
    }

    @GetMapping("/restaurants/{restaurantId}/reservations/available-tables")
    public TableSetDto getAvailableTables(
            @PathVariable Long restaurantId,
            @RequestParam(name = "startTime") LocalDateTime startTime,
            @RequestParam(name = "endTime") LocalDateTime endTime,
            @RequestParam(name = "nPeople") int nPeople
            ) {
        return new TableSetDto(restaurantService.findAvailableTables(restaurantId, startTime, endTime, nPeople));
    }

    @PostMapping("/restaurants/{restaurantId}/reservations")
    public void reserveTable(@PathVariable Long restaurantId, @RequestBody ReservationDto reservationDto) {
        restaurantService.reserveTable(restaurantId, reservationDto);
    }

    @DeleteMapping("/restaurants/{restaurantId}/reservations/{reservationId}")
    public void cancelReservation(@PathVariable Long restaurantId, @PathVariable Long reservationId) {
        restaurantService.cancelReservations(restaurantId, reservationId);
    }

    @PutMapping("/restaurants/{restaurantId}/reservations/{reservationId}")
    public ReservationSetDto updateReservation(
            @PathVariable Long restaurantId,
            @PathVariable Long reservationId,
            @RequestBody ReservationDto reservationDto) {
        return null;
    }
}
