package com.solaredge.restaurantreservations.mappers;

import com.solaredge.restaurantreservations.api.model.ReservationDto;
import com.solaredge.restaurantreservations.domain.Reservation;

public class ReservationMapper {
    public static final ReservationMapper INSTANCE = new ReservationMapper();

    public Reservation reservationDtoToReservation(ReservationDto reservationDto) {
        if (reservationDto == null) {
            return null;
        }

        return new Reservation(
                reservationDto.getId(),
                reservationDto.getName(),
                reservationDto.getStartTime(),
                reservationDto.getEndTime(),
                reservationDto.getTableName(),
                reservationDto.getNPeople()
        );
    }

    public ReservationDto reservationToReservationDto(Reservation reservation) {
        return reservation == null ?
                null :
                new ReservationDto(
                        reservation.getId(),
                        reservation.getName(),
                        reservation.getStartTime(),
                        reservation.getEndTime(),
                        reservation.getTableName(),
                        reservation.getNPeople()
                );
    }
}
