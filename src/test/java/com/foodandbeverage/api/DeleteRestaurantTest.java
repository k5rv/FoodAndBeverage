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
class DeleteRestaurantTest {

  private static final String STATUS_404 = "\"status\":404";
  private static final String ERROR_RESTAURANT_WITH_GIVEN_ID_NOT_FOUND =
      "\"error\":\"restaurant with given id not found\"";

  @Autowired private RestaurantClient underTest;

  @Autowired private RestaurantMapper mapper;

  @BeforeEach
  void setUp() {
    underTest.reset();
  }

  @Test
  void itShouldDeleteRestaurant() {
    // Given
    Restaurant restaurant = RestaurantHelper.getRestaurant();
    underTest.createRestaurant(restaurant);
    // When
    underTest.deleteRestaurant(restaurant.id());
    // Then
    List<Restaurant> restaurants = mapper.toDtos(underTest.getRestaurants());
    assertThat(restaurant).isNotIn(restaurants);
  }

  @ParameterizedTest
  @CsvSource(
      delimiter = '|',
      nullValues = "null",
      textBlock =
          """
           123|
          -123|
          null|
          ''  |
          abc |
          !@#$%^&*()-_+={}[]\\:;"'<>,./|
          0.1 |
          """)
  void itShouldReturnNotFoundIfRestaurantDoesNotExistWhenDeleting(String id) {
    // Then
    assertThatThrownBy(() -> underTest.deleteRestaurant(id))
        .isExactlyInstanceOf(FeignException.NotFound.class)
        .hasMessageContaining(STATUS_404)
        .hasMessageContaining(ERROR_RESTAURANT_WITH_GIVEN_ID_NOT_FOUND);
  }
}
