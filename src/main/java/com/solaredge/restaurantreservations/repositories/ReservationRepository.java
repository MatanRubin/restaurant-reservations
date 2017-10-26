package com.solaredge.restaurantreservations.repositories;

import com.solaredge.restaurantreservations.domain.Reservation;
import com.solaredge.restaurantreservations.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
