package com.foodandbeverage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;

@Builder
public record GetRestaurantsResponse(
    Boolean success, @JsonProperty("data") List<RestaurantDto> restaurantDtos) {}
