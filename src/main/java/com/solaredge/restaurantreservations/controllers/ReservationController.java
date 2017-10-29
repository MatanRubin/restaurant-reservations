package com.solaredge.restaurantreservations.controllers;

import com.solaredge.restaurantreservations.api.model.ReservationDto;
import com.solaredge.restaurantreservations.api.model.ReservationSetDto;
import com.solaredge.restaurantreservations.api.model.TableSetDto;
import com.solaredge.restaurantreservations.services.ReservationService;
import com.solaredge.restaurantreservations.services.RestaurantService;
import javassist.tools.web.BadHttpRequest;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Log
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
    @ResponseStatus(HttpStatus.OK)
    public ReservationSetDto getAllReservations(@PathVariable Long restaurantId) {
        return new ReservationSetDto(restaurantService.getReservationsForRestaurant(restaurantId));
    }

    @GetMapping("/restaurants/{restaurantId}/reservations/{reservationId}")
    @ResponseStatus(HttpStatus.OK)
    public ReservationDto getReservationInfo(@PathVariable Long restaurantId, @PathVariable Long reservationId) {
        return reservationService.getReservationById(reservationId);
    }

    @GetMapping("/restaurants/{restaurantId}/reservations/available-tables")
    @ResponseStatus(HttpStatus.OK)
    public TableSetDto getAvailableTables(
            @PathVariable Long restaurantId,
            @RequestParam(name = "startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(name = "endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
            @RequestParam(name = "nPeople") int nPeople
    ) {
        return new TableSetDto(restaurantService.findAvailableTables(restaurantId, startTime, endTime, nPeople));
    }

    @PostMapping("/restaurants/{restaurantId}/reservations")
    @ResponseStatus(HttpStatus.CREATED)
    public void reserveTable(@PathVariable Long restaurantId, @RequestBody ReservationDto reservationDto) {
        restaurantService.reserveTable(restaurantId, reservationDto);
    }

    @DeleteMapping("/restaurants/{restaurantId}/reservations/{reservationId}")
    @ResponseStatus(HttpStatus.OK)
    public void cancelReservation(@PathVariable Long restaurantId, @PathVariable Long reservationId) {
        restaurantService.cancelReservations(restaurantId, reservationId);
    }

    @PutMapping("/restaurants/{restaurantId}/reservations/{reservationId}")
    public ReservationSetDto updateReservation(
            @PathVariable Long restaurantId,
            @PathVariable Long reservationId,
            @Valid @RequestBody ReservationDto reservationDto) {
        return null;
    }
}
