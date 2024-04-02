package com.foodandbeverage.pages;

import com.foodandbeverage.dto.Restaurant;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RestaurantPage extends Page {

  @FindBy(xpath = "//button[contains(text(),'Create new')]")
  private WebElement createRestaurantButton;

  @FindBy(id = "id")
  private WebElement restaurantIdInput;

  @FindBy(id = "name")
  private WebElement restaurantNameInput;

  @FindBy(id = "score")
  private WebElement restaurantScoreInput;

  @FindBy(id = "address")
  private WebElement restaurantAddressInput;

  @FindBy(xpath = "//button[contains(text(),'Submit')]")
  private WebElement submitRestaurantDataButton;

  @FindBy(xpath = "//button[contains(text(),'OK')]")
  private WebElement restaurantActionAlertButton;

  @FindBy(id = "main-table")
  private WebElement restaurantTable;

  @FindBy(css = "tbody")
  private WebElement restaurantRows;

  public RestaurantPage(WebDriver driver) {
    super(driver);
  }

  public void open() {
    this.driver.get("https://testomate-test.web.app/home");
  }

  public void createRestaurant(Restaurant restaurant) {
    createRestaurantButton.click();
    restaurantIdInput.clear();
    restaurantIdInput.sendKeys(restaurant.id());
    restaurantNameInput.clear();
    restaurantNameInput.sendKeys(restaurant.name());
    restaurantScoreInput.clear();
    restaurantScoreInput.sendKeys(restaurant.score());
    restaurantAddressInput.clear();
    restaurantAddressInput.sendKeys(restaurant.address());
    submitRestaurantDataButton.click();
    isVisible(restaurantActionAlertButton).click();
  }

  public List<Restaurant> getRestaurants() {
    List<WebElement> restaurants = restaurantRows.findElements(By.tagName("tr"));
    return restaurants.stream()
        .map(restaurant -> restaurant.findElements(By.tagName("td")))
        .map(
            fields ->
                Restaurant.builder()
                    .id(fields.get(1).getText())
                    .name(fields.get(2).getText())
                    .address(fields.get(3).getText())
                    .score(fields.get(4).getText())
                    .build())
        .toList();
  }

  public void deleteRestaurant(String id) {
    isVisible(restaurantTable);
    isVisible(restaurantRows);
    List<WebElement> restaurants = restaurantRows.findElements(By.tagName("tr"));

    restaurants.stream()
        .map(restaurant -> restaurant.findElements(By.tagName("td")))
        .filter(fields -> fields.get(1).getText().equals(id))
        .findFirst()
        .ifPresent(
            fields -> {
              WebElement deleteRestaurantButton = fields.get(5);
              isVisible(deleteRestaurantButton).click();
              isVisible(restaurantActionAlertButton).click();
            });
  }
}
