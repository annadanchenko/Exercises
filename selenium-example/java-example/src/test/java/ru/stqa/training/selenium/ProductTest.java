package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class ProductTest {
    private WebDriver driver;
    private WebDriverWait wait;

    float convertPXtoFloat(String str){
        String conv = str.substring(0, str.length()-2);
        return Float.parseFloat(conv);
    }

    @Before
    public void start() {
        driver = new ChromeDriver();
       //  FirefoxBinary bin = new FirefoxBinary(new File("c:\\Program Files\\Nightly\\firefox.exe"));
        // driver = new FirefoxDriver(bin, new FirefoxProfile());
        // driver = new InternetExplorerDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void productTest() {
        driver.get("http://localhost/litecart/en/");

        WebElement productHome = driver.findElement(By.cssSelector("div#box-campaigns li.product:first-child"));
        WebElement campaignPriceElement = productHome.findElement(By.className("campaign-price"));
        WebElement regularPriceElement = productHome.findElement(By.className("regular-price"));

        String titleHome = productHome.findElement(By.cssSelector("div.name")).getAttribute("textContent");
        String campaignPriceHome= campaignPriceElement.getAttribute("textContent");
        String regularPriceHome= regularPriceElement.getAttribute("textContent");

        String campaignPriceColorHome = campaignPriceElement.getCssValue("color");
        String campaignPriceSizeHome = campaignPriceElement.getCssValue("font-size");
        String campaignPriceTagHome = campaignPriceElement.getTagName();

        String regularPriceColorHome= regularPriceElement.getCssValue("color");
        String regularPriceSizeHome = regularPriceElement.getCssValue("font-size");
        String regularPriceTagHome = regularPriceElement.getTagName();

        //в) обычная цена серая и зачёркнутая, а акционная цена красная и жирная
        Assert.assertTrue(regularPriceTagHome.equals("s"));
        Assert.assertTrue(campaignPriceTagHome.equals("strong"));

        if(driver instanceof FirefoxDriver) {
            Assert.assertTrue(regularPriceColorHome.equals("rgb(119, 119, 119)"));
            Assert.assertTrue(campaignPriceColorHome.equals("rgb(204, 0, 0)"));
        }
        else {
            Assert.assertTrue(regularPriceColorHome.equals("rgba(119, 119, 119, 1)"));
            Assert.assertTrue(campaignPriceColorHome.equals("rgba(204, 0, 0, 1)"));
        }

        //г) акционная цена крупнее, чем обычная
        Assert.assertTrue(convertPXtoFloat(campaignPriceSizeHome) > convertPXtoFloat(regularPriceSizeHome));

        productHome.click();

        WebElement PDP = driver.findElement(By.cssSelector("div#box-product"));
        WebElement campaignPDP = PDP.findElement(By.className("campaign-price"));
        WebElement regularPDP = PDP.findElement(By.className("regular-price"));

        String titlePDP = PDP.findElement(By.cssSelector("h1.title")).getAttribute("textContent");
        String campaignPricePDP = campaignPDP.getAttribute("textContent");
        String regularPricePDP= regularPDP.getAttribute("textContent");

        String campaignPriceColorPDP = campaignPDP.getCssValue("color");
        String campaignPriceSizePDP = campaignPDP.getCssValue("font-size");
        String campaignPriceTagPDP = campaignPDP.getTagName();

        String regularPriceColorPDP = regularPDP.getCssValue("color");
        String regularPriceSizePDP = regularPDP.getCssValue("font-size");
        String regularPriceTagPDP = regularPDP.getTagName();

        //  а) на главной странице и на странице товара совпадает текст названия товара
        Assert.assertTrue(titleHome.equals(titlePDP));

        //  б) на главной странице и на странице товара совпадают цены (обычная и акционная)
        Assert.assertTrue(campaignPriceHome.equals(campaignPricePDP));
        Assert.assertTrue(regularPriceHome.equals(regularPricePDP));

        //в) обычная цена серая и зачёркнутая, а акционная цена красная и жирная
        Assert.assertTrue(regularPriceTagPDP.equals("s"));
        Assert.assertTrue(campaignPriceTagPDP.equals("strong"));

        if(driver instanceof FirefoxDriver) {
            Assert.assertTrue(regularPriceColorPDP.equals("rgb(102, 102, 102)"));
            Assert.assertTrue(campaignPriceColorPDP.equals("rgb(204, 0, 0)"));
        }
        else {
            Assert.assertTrue(regularPriceColorPDP.equals("rgba(102, 102, 102, 1)"));
            Assert.assertTrue(campaignPriceColorPDP.equals("rgba(204, 0, 0, 1)"));
        }
        //г) акционная цена крупнее, чем обычная
        Assert.assertTrue(convertPXtoFloat(campaignPriceSizePDP) > convertPXtoFloat(regularPriceSizePDP));
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
