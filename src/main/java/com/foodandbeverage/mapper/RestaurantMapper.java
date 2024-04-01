package com.foodandbeverage.mapper;

import com.foodandbeverage.dto.GetRestaurantsResponse;
import com.foodandbeverage.dto.Restaurant;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RestaurantMapper {

  default List<Restaurant> toDtos(GetRestaurantsResponse response) {
    if (response == null) return List.of();
    if (response.restaurants() == null) return List.of();
    return response.restaurants();
  }
}
