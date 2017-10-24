package com.solaredge.restaurantreservations.services;

import com.solaredge.restaurantreservations.api.model.RestaurantDto;
import com.solaredge.restaurantreservations.api.model.TableDto;
import com.solaredge.restaurantreservations.domain.Restaurant;
import com.solaredge.restaurantreservations.domain.Table;
import com.solaredge.restaurantreservations.mappers.RestaurantMapper;
import com.solaredge.restaurantreservations.mappers.TableMapper;
import com.solaredge.restaurantreservations.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final TableService tableService;

    private final RestaurantMapper restaurantMapper = RestaurantMapper.INSTANCE;
    private final TableMapper tableMapper = TableMapper.INSTANCE;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, TableService tableService) {
        this.restaurantRepository = restaurantRepository;
        this.tableService = tableService;
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
        return restaurantMapper.restaurantToRestaurantDto(restaurantRepository.findById(id).orElseGet(null));
    }

    @Override
    public void deleteRestaurant(Long id) {
        restaurantRepository.deleteById(id);
    }

    @Override
    public TableDto addTableToRestaurant(Long restaurantId, TableDto tableDto) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(restaurantId);
        if (!restaurantOptional.isPresent()) {
            // TODO handle errors
        }
        Restaurant restaurant = restaurantOptional.get();

        TableDto createdTable = tableService.createTable(tableDto);
        restaurant.addTable(tableMapper.tableDtoToTable(createdTable));
        restaurantRepository.save(restaurant);
        return tableDto;
    }
}
