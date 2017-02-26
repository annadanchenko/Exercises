package com.dan.app;

import com.dan.pages.BusketPage;
import com.dan.pages.HomePage;
import com.dan.pages.ProductPage;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Application {
    private WebDriver driver;

    private HomePage homePage;
    private ProductPage productPage;
    private BusketPage busketPage;

    public Application() {
        driver = new ChromeDriver();
        homePage = new HomePage(driver);
        productPage = new ProductPage(driver);
        busketPage = new BusketPage(driver);
    }

    public void quit() {
        driver.quit();
    }

    public void addProductsToCart(int numberOfProducts)
    {
        for (int i = 1; i <= numberOfProducts; i++) {
            homePage.open();
            homePage.openFirstProduct();
            productPage.addProductToCart();
        }
    }

    public void  removeAllProductsFromCart(){
        busketPage.open();
        busketPage.removeAllProductsOneByOne();
    }

    public void verifyCartIsEmpty(){
       Assert.assertTrue(busketPage.isEmpty());
    }

}
