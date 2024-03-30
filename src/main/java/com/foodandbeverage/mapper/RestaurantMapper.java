package com.foodandbeverage.mapper;

import com.foodandbeverage.dto.GetRestaurantsResponse;
import com.foodandbeverage.dto.RestaurantDto;
import com.foodandbeverage.model.Restaurant;
import java.util.List;
import java.util.Objects;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RestaurantMapper {

  default RestaurantDto toDto(Restaurant restaurant) {
    if (restaurant == null) return null;
    String id = restaurant.getId() == null ? "" : String.valueOf(restaurant.getId());
    String name = restaurant.getName() == null ? "" : restaurant.getName();
    String score = restaurant.getScore() == null ? "" : String.valueOf(restaurant.getScore());
    String address = restaurant.getAddress() == null ? "" : restaurant.getAddress();
    return RestaurantDto.builder().id(id).name(name).score(score).address(address).build();
  }

  Restaurant toModel(RestaurantDto restaurantDto);

  default List<Restaurant> toModel(GetRestaurantsResponse response) {
    if (response == null) return List.of();
    if (response.restaurantDtos() == null) return List.of();
    return response.restaurantDtos().stream().filter(Objects::nonNull).map(this::toModel).toList();
  }
}
