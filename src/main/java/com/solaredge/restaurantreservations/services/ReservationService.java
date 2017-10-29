package com.solaredge.restaurantreservations.services;

import com.solaredge.restaurantreservations.api.model.ReservationDto;
import com.solaredge.restaurantreservations.domain.Reservation;
import com.solaredge.restaurantreservations.exceptions.NotFoundException;
import com.solaredge.restaurantreservations.mappers.ReservationMapper;
import com.solaredge.restaurantreservations.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper = ReservationMapper.INSTANCE;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public ReservationDto getReservationById(Long id) {
        Reservation reservation = reservationRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find reservation with id=" + id));
        return reservationMapper.reservationToReservationDto(reservation);
    }
}
