package com.foodandbeverage.dto;

import lombok.Builder;

@Builder
public record Restaurant(String id, String name, String score, String address) {}
