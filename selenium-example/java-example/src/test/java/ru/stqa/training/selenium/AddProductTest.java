package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


public class AddProductTest {
    private WebDriver driver;
    private WebDriverWait wait;

    private void login() {
        driver.get("http://localhost/litecart/admin");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }

    private void setSelectToIndex(By locator, int index) {
        WebElement listElement = driver.findElement(locator);
        Select dropdown = new Select(listElement);
        dropdown.selectByIndex(index);
    }

    private void cleanSetValue(By locator, String value){
        WebElement element = driver.findElement(locator);
        element.clear();
        element.sendKeys(value);
    }

    @Before
    public void start() {
        driver = new ChromeDriver();
        //  driver = new InternetExplorerDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void addProductTest() {
        login();
        driver.findElement(By.cssSelector("ul#box-apps-menu li:nth-child(2)")).click();
        driver.findElement(By.cssSelector("a.button:nth-child(2)")).click();

        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        String productName = "Product " + randomUUIDString;
        String productCode = "Code " + randomUUIDString;

        driver.findElement(By.cssSelector("input[name='status'][value='1']")).click();
        driver.findElement(By.name("name[en]")).sendKeys(productName);
        driver.findElement(By.name("code")).sendKeys(productCode);
        driver.findElement(By.cssSelector("input[data-name='Rubber Ducks']")).click();
        driver.findElement(By.cssSelector("input[name='product_groups[]'][value='1-3']")).click();
        setSelectToIndex(By.name("default_category_id"), 1);
        cleanSetValue(By.name("quantity"), "20");
        driver.findElement(By.name("date_valid_from")).sendKeys("28-01-2016");
        driver.findElement(By.name("date_valid_to")).sendKeys("15-11-2026");

        File file = null;
        try {
            file = new File(getClass().getClassLoader().getResource("green.jpg").toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(file.exists());
        WebElement browseButton = driver.findElement(By.cssSelector("input[name='new_images[]']"));
        browseButton.sendKeys(file.getAbsolutePath());

        driver.findElement(By.cssSelector("ul.index li:nth-child(2)")).click();

        setSelectToIndex(By.name("manufacturer_id"), 1);
        driver.findElement(By.cssSelector("div.trumbowyg-editor")).sendKeys("Text for description");
        driver.findElement(By.name("head_title[en]")).sendKeys("Text for head title");
        driver.findElement(By.name("meta_description[en]")).sendKeys("Text for metadescription");
        driver.findElement(By.name("short_description[en]")).sendKeys("Text for short description");
        driver.findElement(By.name("keywords")).sendKeys("keyword");

        driver.findElement(By.cssSelector("ul.index li:nth-child(4)")).click();

        cleanSetValue(By.name("purchase_price"), "100");
        setSelectToIndex(By.name("purchase_price_currency_code"), 1);
        cleanSetValue(By.name("gross_prices[USD]"), "120");
        cleanSetValue(By.name("prices[EUR]"), "126");
        driver.findElement(By.name("save")).click();

        List<WebElement> products = driver.findElements(By.xpath("//table[contains(@class, 'dataTable')]/tbody/tr/td[3]"));
        List<String> productTitles = new ArrayList<String>();

        for (int i = 0; i < products.size(); i++) {
            productTitles.add(products.get(i).getText());
        }

        Assert.assertTrue(productTitles.contains(productName));
    }
    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
