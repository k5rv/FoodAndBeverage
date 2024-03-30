package com.foodandbeverage.client;

import com.foodandbeverage.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "restaurant")
public interface RestaurantClient {

  @PostMapping(path = "restaurant")
  CreateRestaurantResponse createRestaurant(@RequestBody RestaurantDto request);

  @GetMapping(path = "restaurants")
  GetRestaurantsResponse getRestaurants();

  @DeleteMapping(path = "restaurant/{restaurant_id}")
  DeleteRestaurantResponse deleteRestaurant(
      @PathVariable(value = "restaurant_id") Integer restaurantId);

  @PostMapping(path = "reset")
  ResetResponse reset();

  @PatchMapping(path = "restaurant/{restaurant_id}")
  DeleteRestaurantResponse updateRestaurant(
      @PathVariable(value = "restaurant_id") Integer restaurantId,
      @RequestBody RestaurantDto request);
}
