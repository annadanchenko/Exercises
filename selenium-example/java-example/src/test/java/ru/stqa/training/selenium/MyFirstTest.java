package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.lang.String;
import java.util.Arrays;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.FirefoxBinary;
import java.io.File;
import java.util.concurrent.TimeUnit;


public class MyFirstTest {
    private WebDriver driver;
    private WebDriverWait wait;

    public boolean isElementPresent(By locator) {
        try {
            return driver.findElements(locator).size() > 0;
        } catch (InvalidSelectorException ex) {
            throw ex;
        }
    }
    void checkItem(By selector){
        WebElement item = driver.findElement(selector);
        item.click();
        Assert.assertTrue(isElementPresent(By.cssSelector("h1")));
    }



    @Before
    public void start() {
       // FirefoxBinary bin = new FirefoxBinary(new File("c:\\Program Files\\Nightly\\firefox.exe"));
        // driver = new FirefoxDriver(bin, new FirefoxProfile());
        //  driver = new InternetExplorerDriver();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }


    @Test
    public void stickerTest() {
        driver.get("http://localhost/litecart/en/");
        List<String> groupLocators = Arrays.asList("div#box-most-popular li.product", 
                                                    "div#box-campaigns li.product", 
                                                    "div#box-latest-products li.product");
        for(String groupLocator : groupLocators)
        {
            List<WebElement> productList = driver.findElements(By.cssSelector(groupLocator));
            for (int i = 0; i < productList.size(); i++) {
                String itemNumber = Integer.toString(i + 1);
                String locator = String.format("%s:nth-child(%s) div.sticker", groupLocator, itemNumber);
                List<WebElement> stickers = driver.findElements(By.cssSelector(locator));
                Assert.assertTrue(stickers.size() == 1);
            }
        }
    }

   // @Test
    public void menuTest() {
        driver.get("http://localhost/litecart/admin");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        List<WebElement> mainItems = driver.findElements(By.cssSelector("ul#box-apps-menu span.icon-wrapper"));
        for (int i = 0; i < mainItems.size(); i++) {
            String mainItemNumber = Integer.toString(i+1);
            String mainLocator = String.format("ul#box-apps-menu li:nth-child(%s) span.icon-wrapper",mainItemNumber);
            checkItem(By.cssSelector(mainLocator));
            List<WebElement> subItems = driver.findElements(By.cssSelector("ul.docs li"));
            for (int j = 0; j < subItems.size(); j++) {
                String subItemNumber = Integer.toString(j+1);
                String subLocator = String.format("ul.docs li:nth-child(%s)",subItemNumber);
                checkItem(By.cssSelector(subLocator));
            }
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
