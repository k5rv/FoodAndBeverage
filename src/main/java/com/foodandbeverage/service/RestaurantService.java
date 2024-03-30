package com.foodandbeverage.service;

import com.foodandbeverage.client.RestaurantClient;
import com.foodandbeverage.dto.GetRestaurantsResponse;
import com.foodandbeverage.dto.RestaurantDto;
import com.foodandbeverage.mapper.RestaurantMapper;
import com.foodandbeverage.model.Restaurant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class RestaurantService {
  private final RestaurantClient restaurantClient;
  private final RestaurantMapper restaurantMapper;

  public List<Restaurant> getRestaurants() {
    GetRestaurantsResponse response = restaurantClient.getRestaurants();
    return restaurantMapper.toModel(response);
  }

  public void createRestaurant(Restaurant restaurant) {
    RestaurantDto restaurantDto = restaurantMapper.toDto(restaurant);
    restaurantClient.createRestaurant(restaurantDto);
  }

  public void deleteRestaurant(Integer restaurantId) {
    restaurantClient.deleteRestaurant(restaurantId);
  }

  public void updateRestaurant(Restaurant restaurant) {
    RestaurantDto restaurantDto = restaurantMapper.toDto(restaurant);
    Integer restaurantId = restaurant.getId();
    restaurantClient.updateRestaurant(restaurantId, restaurantDto);
  }

  public void deleteRestaurants() {
    restaurantClient.reset();
  }
}
