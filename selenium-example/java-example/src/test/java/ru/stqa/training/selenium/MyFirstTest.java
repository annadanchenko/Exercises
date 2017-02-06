package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

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
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void menuTest() {
        driver.get("http://localhost/litecart/admin");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        try{
            //Appearence
            checkItem(By.cssSelector("ul#box-apps-menu li:first-child"));
            checkItem(By.id("doc-template"));
            checkItem(By.id("doc-logotype"));
            //Catalog
            checkItem(By.cssSelector("ul#box-apps-menu li:nth-child(2) span.icon-wrapper"));
            checkItem(By.id("doc-catalog"));
            checkItem(By.id("doc-product_groups"));
            checkItem(By.id("doc-option_groups"));
            checkItem(By.id("doc-manufacturers"));
            checkItem(By.id("doc-suppliers"));
            checkItem(By.id("doc-delivery_statuses"));
            checkItem(By.id("doc-sold_out_statuses"));
            checkItem(By.id("doc-quantity_units"));
            checkItem(By.id("doc-csv"));
            //Countries
            checkItem(By.cssSelector("ul#box-apps-menu li:nth-child(3) span.icon-wrapper"));
            //Currencies
            checkItem(By.cssSelector("ul#box-apps-menu li:nth-child(4) span.icon-wrapper"));
            //Customers
            checkItem(By.cssSelector("ul#box-apps-menu li:nth-child(5) span.icon-wrapper"));
            checkItem(By.id("doc-customers"));
            checkItem(By.id("doc-csv"));
            checkItem(By.id("doc-newsletter"));
            //Geo Zones
            checkItem(By.cssSelector("ul#box-apps-menu li:nth-child(6) span.icon-wrapper"));
            //Languages
            checkItem(By.cssSelector("ul#box-apps-menu li:nth-child(7) span.icon-wrapper"));
            checkItem(By.id("doc-languages"));
            checkItem(By.id("doc-storage_encoding"));
            //Modules
            checkItem(By.cssSelector("ul#box-apps-menu li:nth-child(8) span.icon-wrapper"));
            checkItem(By.id("doc-jobs"));
            checkItem(By.id("doc-customer"));
            checkItem(By.id("doc-shipping"));
            checkItem(By.id("doc-payment"));
            checkItem(By.id("doc-order_total"));
            checkItem(By.id("doc-order_success"));
            checkItem(By.id("doc-order_action"));
            //Orders
            checkItem(By.cssSelector("ul#box-apps-menu li:nth-child(9) span.icon-wrapper"));
            checkItem(By.id("doc-orders"));
            checkItem(By.id("doc-order_statuses"));
            //Pages
            checkItem(By.cssSelector("ul#box-apps-menu li:nth-child(10) span.icon-wrapper"));
            //Reports
            checkItem(By.cssSelector("ul#box-apps-menu li:nth-child(11) span.icon-wrapper"));
            checkItem(By.id("doc-monthly_sales"));
            checkItem(By.id("doc-most_sold_products"));
            checkItem(By.id("doc-most_shopping_customers"));
            //Settings
            checkItem(By.cssSelector("ul#box-apps-menu li:nth-child(12) span.icon-wrapper"));
            checkItem(By.id("doc-store_info"));
            checkItem(By.id("doc-defaults"));
            checkItem(By.id("doc-general"));
            checkItem(By.id("doc-listings"));
            checkItem(By.id("doc-images"));
            checkItem(By.id("doc-checkout"));
            checkItem(By.id("doc-advanced"));
            checkItem(By.id("doc-security"));
            //Slides
            checkItem(By.cssSelector("ul#box-apps-menu li:nth-child(13) span.icon-wrapper"));
            //Tax
            checkItem(By.cssSelector("ul#box-apps-menu li:nth-child(14) span.icon-wrapper"));
            checkItem(By.id("doc-tax_classes"));
            checkItem(By.id("doc-tax_rates"));
            //Translations
            checkItem(By.cssSelector("ul#box-apps-menu li:nth-child(15) span.icon-wrapper"));
            checkItem(By.id("doc-search"));
            checkItem(By.id("doc-scan"));
            checkItem(By.id("doc-csv"));
            //Users
            checkItem(By.cssSelector("ul#box-apps-menu li:nth-child(16) span.icon-wrapper"));
            //vQmods
            checkItem(By.cssSelector("ul#box-apps-menu li:nth-child(17) span.icon-wrapper"));
            checkItem(By.id("doc-vqmods"));

        } catch (NoSuchElementException  e) {
            Assert.fail("element not found");
        }
    }


    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
