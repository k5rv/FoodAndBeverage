package com.foodandbeverage.helper;

import com.foodandbeverage.model.Restaurant;
import java.math.BigDecimal;
import org.apache.commons.lang3.RandomStringUtils;

public class RestaurantHelper {

  public static Restaurant getRestaurant() {
    Integer id = getRandomId();
    String name = getRandomName();
    String address = getRandomAddress();
    BigDecimal score = BigDecimal.valueOf(4.9);
    return Restaurant.builder().id(id).name(name).address(address).score(score).build();
  }

  public static Integer getRandomId() {
    return Integer.valueOf(RandomStringUtils.randomNumeric(4));
  }

  public static String getRandomName() {
    return RandomStringUtils.randomAlphabetic(5, 15);
  }

  public static String getRandomAddress() {
    return getRandomName() + " " + getRandomName() + " " + getRandomName();
  }
}
