package com.solaredge.restaurantreservations.repositories;

import com.solaredge.restaurantreservations.domain.Table;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableRepository extends JpaRepository<Table, Long> {
}
