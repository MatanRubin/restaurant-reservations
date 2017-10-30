package com.solaredge.restaurantreservations;

import com.solaredge.restaurantreservations.api.model.*;
import com.solaredge.restaurantreservations.controllers.TestUtils;
import lombok.extern.java.Log;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
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

        log.info("creating a restaurant");
        RestaurantDto restaurantDto = new RestaurantDto(null, "Lalala", "Nordau");
        RestaurantDto createdRestaurantDto = restTemplate.postForObject(
                apiUrl + "/restaurants",
                restaurantDto,
                RestaurantDto.class);
        assertNotNull(createdRestaurantDto.getId());
        assertEquals(restaurantDto.getName(), createdRestaurantDto.getName());
        assertEquals(restaurantDto.getAddress(), createdRestaurantDto.getAddress());
        assertEquals(0, restaurantDto.getTableDtos().size());

        log.info("creating a table");
        TableDto tableDto = new TableDto(null, "Big Table", 20);
        TableDto createdTableDto = restTemplate.postForObject(
                apiUrl + "/restaurants/" + createdRestaurantDto.getId() + "/tables",
                tableDto,
                TableDto.class);
        assertNotNull(createdTableDto.getId());
        assertEquals(tableDto.getName(), createdTableDto.getName());
        assertEquals(tableDto.getCapacity(), createdTableDto.getCapacity());

        log.info("getting available tables");
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

        log.info("making sure there are no reservations");
        ReservationSetDto reservations = restTemplate.getForObject(
                UriComponentsBuilder.fromHttpUrl(apiUrl)
                        .pathSegment("restaurants")
                        .pathSegment(createdRestaurantDto.getId().toString())
                        .pathSegment("reservations").build().toUriString(), ReservationSetDto.class);
        assertEquals(0, reservations.getReservations().size());

        log.info("reserving a table");
        ReservationDto reservationDto = new ReservationDto(
                null,
                "Matan Rubin",
                startTime,
                endTime,
                "Big Table",
                nPeople);
        assertEquals(10, reservationDto.getNPeople());

        String s = TestUtils.asJsonString(reservationDto);
        ResponseEntity<ReservationDto> reservationDtoResponseEntity = restTemplate.postForEntity(
                UriComponentsBuilder.fromHttpUrl(apiUrl)
                        .pathSegment("restaurants")
                        .pathSegment(createdRestaurantDto.getId().toString())
                        .pathSegment("reservations")
                        .build().toUriString(),
                reservationDto,
                ReservationDto.class);
        assertEquals(HttpStatus.CREATED, reservationDtoResponseEntity.getStatusCode());

        log.info("make sure the reservation was created");
        reservations = restTemplate.getForObject(
                UriComponentsBuilder.fromHttpUrl(apiUrl)
                        .pathSegment("restaurants")
                        .pathSegment(createdRestaurantDto.getId().toString())
                        .pathSegment("reservations").build().toUriString(), ReservationSetDto.class);
        assertEquals(1, reservations.getReservations().size());
    }
}
