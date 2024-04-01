package com.foodandbeverage.pages;

import java.time.Duration;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

public abstract class Page {
  protected WebDriver driver;
  protected Wait<WebDriver> wait;

  protected Page(WebDriver driver) {
    this.driver = driver;
    this.wait =
        new FluentWait<>(driver)
            .withTimeout(Duration.ofMillis(5000))
            .pollingEvery(Duration.ofMillis(3000))
            .ignoring(ElementNotInteractableException.class);
    PageFactory.initElements(driver, this);
  }

  protected WebElement isVisible(WebElement element) {
    return wait.until(ExpectedConditions.visibilityOf(element));
  }
}
