package com.foodandbeverage.ui;

import static org.assertj.core.api.Assertions.assertThat;

import com.foodandbeverage.client.RestaurantClient;
import com.foodandbeverage.dto.Restaurant;
import com.foodandbeverage.helper.RestaurantHelper;
import com.foodandbeverage.mapper.RestaurantMapper;
import com.foodandbeverage.pages.RestaurantPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(value = "test")
@SpringBootTest()
class RestaurantPageTest {

  private WebDriver driver;

  private RestaurantPage restaurantPage;

  @Autowired private RestaurantClient restaurantClient;

  @Autowired private RestaurantMapper mapper;

  @BeforeEach
  void setUp() {
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    restaurantPage = new RestaurantPage(driver);
    restaurantClient.reset();
  }

  @AfterEach
  void tearDown() {
    driver.close();
    driver.quit();
  }

  @Test
  void createRestaurant() {
    // Given
    Restaurant restaurant = RestaurantHelper.getRestaurant();
    restaurantPage.open();
    // When
    restaurantPage.createRestaurant(restaurant);
    // Then
    List<Restaurant> uiRestaurants = restaurantPage.getRestaurants();
    Assertions.assertThat(restaurant).isIn(uiRestaurants);
    List<Restaurant> apiRestaurants = mapper.toDtos(restaurantClient.getRestaurants());
    assertThat(restaurant).isIn(apiRestaurants);
  }

  @Test
  void deleteRestaurant() {
    // Given
    Restaurant restaurant = RestaurantHelper.getRestaurant();
    restaurantClient.createRestaurant(restaurant);
    restaurantPage.open();
    // When
    restaurantPage.deleteRestaurant(restaurant.id());
    // Then
    List<Restaurant> uiRestaurants = restaurantPage.getRestaurants();
    Assertions.assertThat(restaurant).isNotIn(uiRestaurants);
    List<Restaurant> apiRestaurants = mapper.toDtos(restaurantClient.getRestaurants());
    assertThat(restaurant).isNotIn(apiRestaurants);
  }
}
