package com.foodandbeverage.service;

import static org.assertj.core.api.Assertions.*;

import com.foodandbeverage.helper.RestaurantHelper;
import com.foodandbeverage.model.Restaurant;
import feign.FeignException;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(value = "test")
@SpringBootTest()
class RestaurantServiceTest {

  private static final String ERROR_RESTAURANT_WITH_GIVEN_ID_NOT_FOUND =
      "\"error\":\"restaurant with given id not found\"";

  private static final String ERROR_RESTAURANT_CREATION_FAILED_REQUIRED_FIELDS_ARE_MISSING =
      "\"error\":\"restaurant creation failed required fields are missing\"";
  private static final String STATUS_404 = "\"status\":404";
  private static final String STATUS_400 = "\"status\":400";

  @Autowired private RestaurantService underTest;

  @BeforeEach
  void setUp() {
    underTest.deleteRestaurants();
  }

  @Test
  void itShouldCreateRestaurant() {
    // Given
    Restaurant restaurant = RestaurantHelper.getRestaurant();
    // When
    underTest.createRestaurant(restaurant);
    // Then
    List<Restaurant> restaurants = underTest.getRestaurants();
    assertThat(restaurant).isIn(restaurants);
  }

  @Test
  void itShouldDeleteRestaurant() {
    // Given
    Restaurant restaurant = RestaurantHelper.getRestaurant();
    underTest.createRestaurant(restaurant);
    // When
    underTest.deleteRestaurant(restaurant.getId());
    // Then
    List<Restaurant> restaurants = underTest.getRestaurants();
    assertThat(restaurant).isNotIn(restaurants);
  }

  @Test
  void itShouldReturnNotFoundIfRestaurantDoesNotExistWhenDeleting() {
    // Given
    Integer restaurantId = RestaurantHelper.getRandomId();
    // Then
    assertThatThrownBy(() -> underTest.deleteRestaurant(restaurantId))
        .isExactlyInstanceOf(FeignException.NotFound.class)
        .hasMessageContaining(STATUS_404)
        .hasMessageContaining(ERROR_RESTAURANT_WITH_GIVEN_ID_NOT_FOUND);
  }

  @Test
  void itShouldUpdateRestaurant() {
    // Given
    Restaurant restaurant = RestaurantHelper.getRestaurant();
    underTest.createRestaurant(restaurant);
    // When
    String name = RestaurantHelper.getRandomName();
    restaurant.setName(name);
    underTest.updateRestaurant(restaurant);
    // Then
    List<Restaurant> restaurants = underTest.getRestaurants();
    assertThat(restaurant).isIn(restaurants);
  }

  @Test
  void itShouldReturnNotFoundIfRestaurantDoesNotExistWhenUpdating() {
    // Given
    Restaurant restaurant = RestaurantHelper.getRestaurant();
    // Then
    assertThatThrownBy(() -> underTest.updateRestaurant(restaurant))
        .isExactlyInstanceOf(FeignException.NotFound.class)
        .hasMessageContaining(STATUS_404)
        .hasMessageContaining(ERROR_RESTAURANT_WITH_GIVEN_ID_NOT_FOUND);
  }

  /*

  */
  @ParameterizedTest
  @CsvSource(
      delimiter = '|',
      nullValues = "null",
      textBlock =
       """
         null|John Doe| 4.9|123 Oxford Street, London W1D 1LT|
       123456|    null| 4.9|123 Oxford Street, London W1D 1LT|
       123456|John Doe|null|123 Oxford Street, London W1D 1LT|
       123456|John Doe| 4.9|                             null|
       """)
  void itShouldReturnBadRequestIfDataMissedWhenCreatingRestaurant(
      Integer id, String name, String score, String address) {

    BigDecimal restaurantScore = score == null ? null : new BigDecimal(score);

    // Given
    Restaurant restaurant =
        Restaurant.builder().id(id).name(name).score(restaurantScore).address(address).build();
    // Then
    assertThatThrownBy(() -> underTest.createRestaurant(restaurant))
        .isExactlyInstanceOf(FeignException.NotFound.class)
        .hasMessageContaining(STATUS_400)
        .hasMessageContaining(ERROR_RESTAURANT_CREATION_FAILED_REQUIRED_FIELDS_ARE_MISSING);
  }
}

