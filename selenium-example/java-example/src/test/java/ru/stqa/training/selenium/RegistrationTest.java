package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


public class RegistrationTest {
    private WebDriver driver;
    private WebDriverWait wait;


    @Before
    public void start() {
        driver = new ChromeDriver();
        //  driver = new InternetExplorerDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void registerTest() {
        driver.get("http://localhost/litecart/en/create_account");
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        String email = randomUUIDString+"@mail.com";

        //country
        WebElement country = driver.findElement(By.className("select2-hidden-accessible"));
        Select dropdown = new Select(country);
        dropdown.selectByVisibleText("United States");

        driver.findElement(By.name("firstname")).sendKeys("Firstname");
        driver.findElement(By.name("lastname")).sendKeys("Lastname");
        driver.findElement(By.name("address1")).sendKeys("Address 1");
        driver.findElement(By.name("postcode")).sendKeys("12345");
        driver.findElement(By.name("city")).sendKeys("City");
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("password")).sendKeys("password");
        driver.findElement(By.name("confirmed_password")).sendKeys("password");
        //postcode
        WebElement index = driver.findElement(By.cssSelector("select[name='zone_code']"));
        Select indexDropdown = new Select(index);
        indexDropdown.selectByIndex(30);

        driver.findElement(By.name("phone")).sendKeys("+123456789");
        //click Create account
        driver.findElement(By.cssSelector("button[name='create_account']")).click();

        //logout
        WebElement logout = driver.findElement(By.cssSelector("div#box-account li:last-child a"));
        logout.click();

        //login
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("password")).sendKeys("password");
        driver.findElement(By.name("login")).click();

        //logout
        logout = driver.findElement(By.cssSelector("div#box-account li:last-child a"));
        logout.click();
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
