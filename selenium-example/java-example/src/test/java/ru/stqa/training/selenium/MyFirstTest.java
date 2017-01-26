package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class MyFirstTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
            driver = new ChromeDriver();
            wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void loginTest() {
        driver.get("http://localhost/litecart/admin");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        try{
            WebElement sidebar = driver.findElement(By.id("widget-sales"));
        } catch (NoSuchElementException  e) {
            Assert.fail("widget-sales not found on page after login attempt");
        }
    }


    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
