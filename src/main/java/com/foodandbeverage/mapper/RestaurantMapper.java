package com.foodandbeverage.mapper;

import com.foodandbeverage.dto.GetRestaurantsResponse;
import com.foodandbeverage.dto.RestaurantDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RestaurantMapper {

  default List<RestaurantDto> toDtos(GetRestaurantsResponse response) {
    if (response == null) return List.of();
    if (response.restaurantDtos() == null) return List.of();
    return response.restaurantDtos();
  }
}
