package com.foodandbeverage.helper;

import com.foodandbeverage.dto.RestaurantDto;
import java.util.Random;
import org.apache.commons.lang3.RandomStringUtils;

public class RestaurantHelper {

  public static RestaurantDto getRestaurant() {
    String id = getRandomId();
    String name = getRandomName();
    String address = getRandomAddress();
    String score = "4.2";
    return RestaurantDto.builder().id(id).name(name).address(address).score(score).build();
  }

  public static String getRandomId() {
    return RandomStringUtils.randomNumeric(4);
  }

  public static String getRandomName() {
    return RandomStringUtils.randomAlphabetic(5, 15);
  }

  public static String getRandomAddress() {
    return getRandomName() + " " + getRandomName() + " " + getRandomName();
  }

  public static String getRandomScore() {
    return String.valueOf(getRandomDecimalInRange(0, 5));
  }

  private static double getRandomDecimalInRange(double min, double max) {
    Random r = new Random();
    return (r.nextInt((int) ((max - min) * 10 + 1)) + min * 10) / 10.0;
  }
}
