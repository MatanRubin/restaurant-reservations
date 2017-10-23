package com.solaredge.restaurantreservations.repositories;

import com.solaredge.restaurantreservations.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
