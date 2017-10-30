package com.solaredge.restaurantreservations.services;

import com.solaredge.restaurantreservations.api.model.ReservationDto;
import com.solaredge.restaurantreservations.api.model.RestaurantDto;
import com.solaredge.restaurantreservations.api.model.TableDto;
import com.solaredge.restaurantreservations.domain.Restaurant;
import com.solaredge.restaurantreservations.domain.Table;
import com.solaredge.restaurantreservations.exceptions.NotFoundException;
import com.solaredge.restaurantreservations.mappers.ReservationMapper;
import com.solaredge.restaurantreservations.mappers.RestaurantMapper;
import com.solaredge.restaurantreservations.mappers.TableMapper;
import com.solaredge.restaurantreservations.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final TableService tableService;
    private final ReservationService reservationService;

    private final RestaurantMapper restaurantMapper = RestaurantMapper.INSTANCE;
    private final TableMapper tableMapper = TableMapper.INSTANCE;
    private final ReservationMapper reservationMapper = ReservationMapper.INSTANCE;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, TableService tableService, ReservationService reservationService) {
        this.restaurantRepository = restaurantRepository;
        this.tableService = tableService;
        this.reservationService = reservationService;
    }

    @Override
    public RestaurantDto createRestaurant(RestaurantDto restaurantDto) {
        Restaurant restaurant = restaurantMapper.restaurantDtoToRestaurant(restaurantDto);

        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        return restaurantMapper.restaurantToRestaurantDto(savedRestaurant);
    }

    @Override
    public Set<RestaurantDto> findAllRestaurants() {
        return restaurantRepository.findAll().stream()
                .map(restaurantMapper::restaurantToRestaurantDto)
                .collect(Collectors.toSet());
    }

    @Override
    public RestaurantDto getRestaurantById(Long id) {
        Restaurant restaurant = restaurantRepository.
                findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find restaurant with id=" + id));

        return restaurantMapper.restaurantToRestaurantDto(restaurant);
    }

    @Override
    public Set<ReservationDto> getReservationsForRestaurant(Long restaurantId) {
        return restaurantRepository
                .findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Can't find restaurant with restaurantId=" + restaurantId))
                .getTables().values().stream()
                .map(table -> table.getReservationsById().values())
                .flatMap(Collection::stream)
                .map(reservationMapper::reservationToReservationDto)
                .collect(Collectors.toSet());
    }

    @Override
    public void deleteRestaurant(Long id) {
        restaurantRepository.deleteById(id);
    }

    @Override
    public TableDto addTableToRestaurant(Long restaurantId, TableDto tableDto) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(restaurantId);
        if (!restaurantOptional.isPresent()) {
            throw new NotFoundException("Can't find restaurant with id=" + restaurantId);
        }
        Restaurant restaurant = restaurantOptional.get();

        TableDto createdTable = tableService.createTable(tableDto);
        restaurant.addTable(tableMapper.tableDtoToTable(createdTable));
        restaurantRepository.save(restaurant);
        return createdTable;
    }

    @Override
    public void removeTableFromRestaurant(Long restaurantId, Long tableId) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(restaurantId);
        if (!restaurantOptional.isPresent()) {
            throw new NotFoundException("Can't find restaurant with id=" + restaurantId);
        }

        Restaurant restaurant = restaurantOptional.get();
        restaurant.removeTable(tableId);
        restaurantRepository.save(restaurant);
    }

    @Override
    public Set<TableDto> findAvailableTables(Long restaurantId, LocalDateTime startTime, LocalDateTime endTime, int nPeople) {
        return restaurantRepository
                .findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Can't find restaurant with id=" + restaurantId))
                .findAvailableTables(startTime, endTime, nPeople).stream()
                .map(tableMapper::tableToTableDto)
                .collect(Collectors.toSet());
    }

    @Override
    public void reserveTable(Long restaurantId, ReservationDto reservationDto) {
        TableDto tableDto = tableService.getTableByName(reservationDto.getTableName());

        Restaurant restaurant = restaurantRepository
                .findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Can't find restaurant with id=" + restaurantId));
        Table table = restaurant.getTables().get(tableDto.getId());
        table.addReservation(reservationMapper.reservationDtoToReservation(reservationDto));
        restaurantRepository.save(restaurant);
    }

    @Override
    public void cancelReservations(Long restaurantId, Long reservationId) {
        ReservationDto reservation = reservationService.getReservationById(reservationId);
        TableDto table = tableService.getTableByName(reservation.getTableName());

        restaurantRepository
                .findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Can't find restaurant with id=" + restaurantId))
                .getTables().get(table.getId())
                .removeReservation(reservationId);
    }
}
