package com.foodandbeverage.api;

import static org.assertj.core.api.Assertions.*;

import com.foodandbeverage.client.RestaurantClient;
import com.foodandbeverage.dto.Restaurant;
import com.foodandbeverage.helper.RestaurantHelper;
import com.foodandbeverage.mapper.RestaurantMapper;
import feign.FeignException;
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
class CreateRestaurantTest {

  private static final String STATUS_400 = "\"status\":400";
  private static final String ERROR_RESTAURANT_CREATION_FAILED_REQUIRED_FIELDS_ARE_MISSING =
      "\"error\":\"restaurant creation failed required fields are missing\"";

  @Autowired private RestaurantClient underTest;

  @Autowired private RestaurantMapper mapper;

  @BeforeEach
  void setUp() {
    underTest.reset();
  }

  @ParameterizedTest
  @CsvSource(
      delimiter = '|',
      textBlock =
          """
          999999|John Doe         |0.1|123 Oxford Street         |
          0     |John9999         |4.9|123 Oxford Street         |
          1     |JohnDoe          |0  |123OxfordStreet           |
          123   |John Doe JR      |3.5|123 Oxford Street         |
          123   |John Doe.,''_-&()|5  |123 ()Ox.,for_d ''Street-&|
          """)
  void itShouldCreateRestaurant(String id, String name, String score, String address) {
    // Given
    Restaurant restaurant =
        Restaurant.builder().id(id).name(name).score(score).address(address).build();
    // When
    underTest.createRestaurant(restaurant);
    // Then
    List<Restaurant> restaurants = mapper.toDtos(underTest.getRestaurants());
    assertThat(restaurant).isIn(restaurants);
  }

  @Test
  void itShouldNotCreateRestaurantIfItIsAlreadyExists() {
    // Given
    Restaurant restaurant = RestaurantHelper.getRestaurant();
    underTest.createRestaurant(restaurant);
    // When
    underTest.createRestaurant(restaurant);
    // Then
    List<Restaurant> restaurants = mapper.toDtos(underTest.getRestaurants());
    assertThat(restaurant).isIn(restaurants);
    assertThat(restaurants.stream().filter(r -> r.id().equals(restaurant.id())).toList())
        .hasSize(1);
  }

  @ParameterizedTest
  @CsvSource(
      delimiter = 'I',
      nullValues = "null",
      textBlock =
          """
          null                          I
          -1                            I
          ''                            I
          abc                           I
          !@#$%^&*()-_+={}[]|\\:;"'<>,./I
          0.1                           I
          2147483648                    I
          """)
  void itShouldReturnBadRequestIfIdIncorrectWhenCreating(String id) {
    // Given
    String name = RestaurantHelper.getRandomName();
    String score = RestaurantHelper.getRandomScore();
    String address = RestaurantHelper.getRandomAddress();
    Restaurant restaurant =
        Restaurant.builder().id(id).name(name).score(score).address(address).build();
    // Then
    assertThatThrownBy(() -> underTest.createRestaurant(restaurant))
        .isExactlyInstanceOf(FeignException.BadRequest.class)
        .hasMessageContaining(STATUS_400)
        .hasMessageContaining(ERROR_RESTAURANT_CREATION_FAILED_REQUIRED_FIELDS_ARE_MISSING);
  }

  @ParameterizedTest
  @CsvSource(
      delimiter = 'I',
      nullValues = "null",
      textBlock =
          """
          ''                            I
          null                          I
          !@#$%^&*()-_+={}[]|\\:;"'<>,./I
          """)
  void itShouldReturnBadRequestIfNameIncorrectWhenCreating(String name) {
    // Given
    String id = RestaurantHelper.getRandomId();
    String score = RestaurantHelper.getRandomScore();
    String address = RestaurantHelper.getRandomAddress();
    Restaurant restaurant =
        Restaurant.builder().id(id).name(name).score(score).address(address).build();
    // Then
    assertThatThrownBy(() -> underTest.createRestaurant(restaurant))
        .isExactlyInstanceOf(FeignException.BadRequest.class)
        .hasMessageContaining(STATUS_400)
        .hasMessageContaining(ERROR_RESTAURANT_CREATION_FAILED_REQUIRED_FIELDS_ARE_MISSING);
  }

  @ParameterizedTest
  @CsvSource(
      delimiter = 'I',
      nullValues = "null",
      textBlock =
          """
          ''                            I
          null                          I
          !@#$%^&*()-_+={}[]|\\:;"'<>,./I
          """)
  void itShouldReturnBadRequestIfAddressIncorrectWhenCreating(String address) {
    // Given
    String id = RestaurantHelper.getRandomId();
    String name = RestaurantHelper.getRandomName();
    String score = RestaurantHelper.getRandomScore();
    Restaurant restaurant =
        Restaurant.builder().id(id).name(name).score(score).address(address).build();
    // Then
    assertThatThrownBy(() -> underTest.createRestaurant(restaurant))
        .isExactlyInstanceOf(FeignException.BadRequest.class)
        .hasMessageContaining(STATUS_400)
        .hasMessageContaining(ERROR_RESTAURANT_CREATION_FAILED_REQUIRED_FIELDS_ARE_MISSING);
  }

  @ParameterizedTest
  @CsvSource(
      delimiter = 'I',
      nullValues = "null",
      textBlock =
          """
          -1                            I
          null                          I
          ''                            I
          abc                           I
          -0.1                          I
          5.1                           I
          3.567                         I
          !@#$%^&*()-_+={}[]|\\:;"'<>,./I
          """)
  void itShouldReturnBadRequestIfScoreIsIncorrectWhenCreating(String score) {
    // Given
    String id = RestaurantHelper.getRandomId();
    String name = RestaurantHelper.getRandomName();
    String address = RestaurantHelper.getRandomAddress();
    Restaurant restaurant =
        Restaurant.builder().id(id).name(name).score(score).address(address).build();
    // Then
    assertThatThrownBy(() -> underTest.createRestaurant(restaurant))
        .isExactlyInstanceOf(FeignException.BadRequest.class)
        .hasMessageContaining(STATUS_400)
        .hasMessageContaining(ERROR_RESTAURANT_CREATION_FAILED_REQUIRED_FIELDS_ARE_MISSING);
  }
}
