package com.solaredge.restaurantreservations;

import com.solaredge.restaurantreservations.api.model.*;
import lombok.extern.java.Log;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Log
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestaurantReservationsApplicationTests {

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Value("${local.server.port}")
    private int port;
    private String apiUrl;

    @Before
    public void setUp() throws Exception {
        apiUrl =  "http://localhost:" + port + "/api";
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void complexFlow() throws Exception {
        log.info("Starting complexFlow test");
        RestaurantSetDto restaurants = restTemplate.getForObject(apiUrl + "/restaurants", RestaurantSetDto.class);
        assertEquals(2, restaurants.getRestaurants().size());

        // create restaurant
        RestaurantDto restaurantDto = new RestaurantDto(null, "Lalala", "Nordau");
        RestaurantDto createdRestaurantDto = restTemplate.postForObject(
                apiUrl + "/restaurants",
                restaurantDto,
                RestaurantDto.class);
        assertNotNull(createdRestaurantDto.getId());
        assertEquals(restaurantDto.getName(), createdRestaurantDto.getName());
        assertEquals(restaurantDto.getAddress(), createdRestaurantDto.getAddress());
        assertEquals(0, restaurantDto.getTableDtos().size());

        // create table
        TableDto tableDto = new TableDto(null, "Big Table", 20);
        TableDto createdTableDto = restTemplate.postForObject(
                apiUrl + "/restaurants/" + createdRestaurantDto.getId() + "/tables",
                tableDto,
                TableDto.class);
        assertNotNull(createdTableDto.getId());
        assertEquals(tableDto.getName(), createdTableDto.getName());
        assertEquals(tableDto.getCapacity(), createdTableDto.getCapacity());

        // show available tables
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(2L);
        int nPeople = 10;
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .pathSegment("restaurants")
                .pathSegment(createdRestaurantDto.getId().toString())
                .pathSegment("reservations")
                .pathSegment("available-tables")
                .queryParam("startTime", startTime)
                .queryParam("endTime", endTime)
                .queryParam("nPeople", nPeople);


        TableSetDto availableTables = restTemplate.getForObject(
                uriBuilder.build().toUriString(),
                TableSetDto.class);
        assertEquals(1, availableTables.getTables().size());

        // reserve table
        ReservationDto reservationDto = new ReservationDto(
                null,
                "Matan Rubin",
                startTime,
                endTime,
                "Big Table",
                nPeople);
        ReservationDto createdReservationDto = restTemplate.postForObject(
                UriComponentsBuilder.fromHttpUrl(apiUrl)
                        .pathSegment("restaurants")
                        .pathSegment(createdRestaurantDto.getId().toString())
                        .pathSegment("reservations")
                        .build().toUriString(),
                reservationDto,
                ReservationDto.class);

        assertNotNull(createdReservationDto.getId());
        assertEquals(reservationDto.getTableName(), createdReservationDto.getTableName());
        assertEquals(reservationDto.getEndTime(), createdReservationDto.getEndTime());
        assertEquals(reservationDto.getStartTime(), createdReservationDto.getStartTime());
        assertEquals(reservationDto.getNPeople(), createdReservationDto.getNPeople());
        assertEquals(reservationDto.getName(), createdReservationDto.getName());
    }
}
