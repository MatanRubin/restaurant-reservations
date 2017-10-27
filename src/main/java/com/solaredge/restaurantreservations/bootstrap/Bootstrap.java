package com.solaredge.restaurantreservations.bootstrap;

import com.solaredge.restaurantreservations.domain.Restaurant;
import com.solaredge.restaurantreservations.domain.Table;
import com.solaredge.restaurantreservations.repositories.RestaurantRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.TreeMap;

@Log
@Component
public class Bootstrap implements CommandLineRunner {
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public Bootstrap(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        Restaurant zozobra = new Restaurant(null, "Zozobra", "Shenkar St.");
        zozobra.addTable(new Table(null, "VIP Table", 19));
        zozobra.addTable(new Table(null, "Window Table", 4));
        zozobra.addTable(new Table(null, "Round Table", 6));

        Restaurant moses = new Restaurant(null, "Moses", "Aba Even St.");
        moses.addTable(new Table(null, "Porch Table", 5));
        moses.addTable(new Table(null, "Big Table", 20));
        moses.addTable(new Table(null, "Table Table", 4));

        restaurantRepository.saveAll(Arrays.asList(zozobra, moses));
        log.info("Bootstrap completed. Created 2 restaurants.");
    }
}
