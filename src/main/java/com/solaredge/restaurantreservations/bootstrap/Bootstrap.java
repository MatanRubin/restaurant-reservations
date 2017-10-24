package com.solaredge.restaurantreservations.bootstrap;

import com.solaredge.restaurantreservations.domain.Restaurant;
import com.solaredge.restaurantreservations.domain.Table;
import com.solaredge.restaurantreservations.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;

@Component
public class Bootstrap implements CommandLineRunner {
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public Bootstrap(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        restaurantRepository.saveAll(Arrays.asList(
                new Restaurant(
                        null,
                        "Zozobra",
                        "Shenkar St.",
                        new HashSet<>(Arrays.asList(
                                new Table(null, "VIP Table", 19),
                                new Table(null, "Window Table", 4),
                                new Table(null, "Round Table", 6)
                        ))),
                new Restaurant(
                        null,
                        "Moses",
                        "Aba Even St.",
                        new HashSet<>(Arrays.asList(
                                new Table(null, "Porch Table", 5),
                                new Table(null, "Big Table", 20),
                                new Table(null, "Table Table", 4)
                        ))))
        );
    }
}
