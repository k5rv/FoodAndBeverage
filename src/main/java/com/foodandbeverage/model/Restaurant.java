package com.foodandbeverage.model;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Restaurant {
  private Integer id;
  private String name;
  private BigDecimal score;
  private String address;
}
