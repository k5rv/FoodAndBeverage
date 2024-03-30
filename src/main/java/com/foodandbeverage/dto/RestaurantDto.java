package com.foodandbeverage.dto;

import lombok.Builder;

@Builder
public record RestaurantDto(String id, String name, String score, String address) {}
