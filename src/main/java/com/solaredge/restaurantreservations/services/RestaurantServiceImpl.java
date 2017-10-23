package com.solaredge.restaurantreservations.services;

import com.solaredge.restaurantreservations.api.model.RestaurantDto;
import com.solaredge.restaurantreservations.domain.Restaurant;
import com.solaredge.restaurantreservations.mappers.RestaurantMapper;
import com.solaredge.restaurantreservations.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper = RestaurantMapper.INSTANCE;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
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
}
