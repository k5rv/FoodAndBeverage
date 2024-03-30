package com.foodandbeverage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FoodandbeverageApplication {

  public static void main(String[] args) {
    SpringApplication.run(FoodandbeverageApplication.class, args);
  }
}
