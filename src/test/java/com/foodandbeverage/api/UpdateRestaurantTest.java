package com.foodandbeverage.api;

import static org.assertj.core.api.Assertions.*;

import com.foodandbeverage.client.RestaurantClient;
import com.foodandbeverage.dto.Restaurant;
import com.foodandbeverage.helper.RestaurantHelper;
import com.foodandbeverage.mapper.RestaurantMapper;
import feign.FeignException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(value = "test")
@SpringBootTest()
class UpdateRestaurantTest {

  private static final String STATUS_404 = "\"status\":404";
  private static final String ERROR_RESTAURANT_WITH_GIVEN_ID_NOT_FOUND =
      "\"error\":\"restaurant with given id not found\"";

  @Autowired private RestaurantClient underTest;

  @Autowired private RestaurantMapper mapper;

  @AfterEach
  void tearDown() {
    underTest.reset();
  }

  @ParameterizedTest
  @CsvSource(
      delimiter = '|',
      nullValues = "null",
      textBlock =
          """
          John Doe   |null|null             |
          null       |4.9 |null             |
          null       |null|123OxfordStreet  |
          John Doe   |0.1 |null             |
          John9999   |null|123 Oxford Street|
          null       |4.9 |123OxfordStreet  |
          John Doe JR|3.5 |123OxfordStreet  |
          """)
  void itShouldUpdateRestaurant(String name, String score, String address) {
    // Given
    Restaurant restaurant = RestaurantHelper.getRestaurant();
    underTest.createRestaurant(restaurant);
    String id = restaurant.id();
    String nameUpdate = name == null ? null : restaurant.name();
    String scoreUpdate = score == null ? null : restaurant.score();
    String addressUpdate = address == null ? null : restaurant.address();
    Restaurant restaurantUpdate =
        Restaurant.builder()
            .id(id)
            .name(nameUpdate)
            .score(scoreUpdate)
            .address(addressUpdate)
            .build();
    // When
    underTest.updateRestaurant(id, restaurantUpdate);
    // Then
    String nameExpected = nameUpdate == null ? restaurant.name() : restaurantUpdate.name();
    String scoreExpected = scoreUpdate == null ? restaurant.score() : restaurantUpdate.score();
    String addressExpected =
        addressUpdate == null ? restaurant.address() : restaurantUpdate.address();
    Restaurant restaurantExpected =
        Restaurant.builder()
            .id(id)
            .name(nameExpected)
            .score(scoreExpected)
            .address(addressExpected)
            .build();
    List<Restaurant> restaurants = mapper.toDtos(underTest.getRestaurants());
    assertThat(restaurantExpected).isIn(restaurants);
  }

  @Test
  void itShouldReturnNotFoundIfRestaurantDoesNotExistWhenUpdating() {
    // Given
    Restaurant restaurant = RestaurantHelper.getRestaurant();
    // Then
    assertThatThrownBy(() -> underTest.updateRestaurant(restaurant.id(), restaurant))
        .isExactlyInstanceOf(FeignException.NotFound.class)
        .hasMessageContaining(STATUS_404)
        .hasMessageContaining(ERROR_RESTAURANT_WITH_GIVEN_ID_NOT_FOUND);
  }

  @ParameterizedTest
  @CsvSource(
      delimiter = '|',
      nullValues = "null",
      textBlock =
          """
          1111111                       I
          -123                          I
          null                          I
          ''                            I
          abc                           I
          0.1                           I
          !@#$%^&*()-_+={}[]|\\:;"'<>,./I
          """)
  void itShouldReturnNotFoundIfRestaurantDoesNotExistWhenUpdating(String id) {
    // Given
    Restaurant restaurant = RestaurantHelper.getRestaurant();
    underTest.createRestaurant(restaurant);
    // Then
    assertThatThrownBy(() -> underTest.updateRestaurant(id, restaurant))
        .isExactlyInstanceOf(FeignException.NotFound.class)
        .hasMessageContaining(STATUS_404)
        .hasMessageContaining(ERROR_RESTAURANT_WITH_GIVEN_ID_NOT_FOUND);
  }

  @Test
  void itShouldNotUpdateRestaurantId() {
    // Given
    Restaurant restaurant = RestaurantHelper.getRestaurant();
    underTest.createRestaurant(restaurant);
    String id = restaurant.id();
    String idUpdate = RestaurantHelper.getRandomId();
    String nameUpdate = RestaurantHelper.getRandomName();
    Restaurant restaurantUpdate =
        Restaurant.builder()
            .id(idUpdate)
            .name(nameUpdate)
            .score(restaurant.score())
            .address(restaurant.address())
            .build();
    // Then
    underTest.updateRestaurant(id, restaurantUpdate);
    List<Restaurant> restaurants = mapper.toDtos(underTest.getRestaurants());
    assertThat(restaurant).isIn(restaurants);
    assertThat(restaurantUpdate).isNotIn(restaurants);
  }

  @ParameterizedTest
  @CsvSource(
      delimiter = 'I',
      nullValues = "null",
      textBlock =
          """
          ''                            I
          !@#$%^&*()-_+={}[]|\\:;"'<>,./I
          """)
  void itShouldNotUpdateIfNameIsIncorrect(String name) {
    // Given
    Restaurant restaurant = RestaurantHelper.getRestaurant();
    underTest.createRestaurant(restaurant);
    String id = restaurant.id();
    String score = restaurant.score();
    String addressUpdate = RestaurantHelper.getRandomAddress();
    Restaurant restaurantUpdate =
        Restaurant.builder().id(id).name(name).score(score).address(addressUpdate).build();
    // Then
    underTest.updateRestaurant(id, restaurantUpdate);
    List<Restaurant> restaurants = mapper.toDtos(underTest.getRestaurants());
    assertThat(restaurant).isIn(restaurants);
    assertThat(restaurantUpdate).isNotIn(restaurants);
  }

  @ParameterizedTest
  @CsvSource(
      delimiter = 'I',
      nullValues = "null",
      textBlock =
          """
          -1                            I
          ''                            I
          abc                           I
          -0.1                          I
          5.1                           I
          3.567                         I
          !@#$%^&*()-_+={}[]|\\:;"'<>,./I
          """)
  void itShouldNotUpdateIfScoreIsIncorrect(String score) {
    // Given
    Restaurant restaurant = RestaurantHelper.getRestaurant();
    underTest.createRestaurant(restaurant);
    String id = restaurant.id();
    String name = restaurant.name();
    String addressUpdate = RestaurantHelper.getRandomAddress();
    Restaurant restaurantUpdate =
        Restaurant.builder().id(id).name(name).score(score).address(addressUpdate).build();
    // Then
    underTest.updateRestaurant(id, restaurantUpdate);
    List<Restaurant> restaurants = mapper.toDtos(underTest.getRestaurants());
    assertThat(restaurant).isIn(restaurants);
    assertThat(restaurantUpdate).isNotIn(restaurants);
  }

  @ParameterizedTest
  @CsvSource(
      delimiter = 'I',
      nullValues = "null",
      textBlock =
          """
          ''                            I
          !@#$%^&*()-_+={}[]|\\:;"'<>,./I
          """)
  void itShouldNotUpdateIfAddressIsIncorrect(String address) {
    // Given
    Restaurant restaurant = RestaurantHelper.getRestaurant();
    underTest.createRestaurant(restaurant);
    String id = restaurant.id();
    String score = restaurant.score();
    String nameUpdate = RestaurantHelper.getRandomName();
    Restaurant restaurantUpdate =
        Restaurant.builder().id(id).name(nameUpdate).score(score).address(address).build();
    // Then
    underTest.updateRestaurant(id, restaurantUpdate);
    List<Restaurant> restaurants = mapper.toDtos(underTest.getRestaurants());
    assertThat(restaurant).isIn(restaurants);
    assertThat(restaurantUpdate).isNotIn(restaurants);
  }
}
