package com.dan.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class AddProductToCartTest {
    private WebDriver driver;
    private WebDriverWait wait;

    private void cleanElementAndSetValue(WebElement element, String value){
        element.clear();
        element.sendKeys(value);
    }

    @Before
    public void start() {
        driver = new ChromeDriver();
       // driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver,35);
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

    public void addProduct()
    {
        // 1) открыть главную страницу
        driver.get("http://localhost/litecart/en/");

        // 2) открыть первый товар из списка
        WebElement productHome = driver.findElement(By.cssSelector("div#box-campaigns li.product:first-child"));
        productHome.click();

        //2) добавить его в корзину (при этом может случайно добавиться товар, который там уже есть, ничего страшного)
        WebElement selectSize = driver.findElement(By.name("options[Size]"));
        Select dropdown = new Select(selectSize);
        dropdown.selectByVisibleText("Small");

        WebElement txtQuantityInCart = driver.findElement(By.cssSelector("span.quantity"));
        String quantityBeforeAddingProduct = txtQuantityInCart.getText();

        WebElement btnAddToCart = driver.findElement(By.name("add_cart_product"));
        btnAddToCart.click();

        // 3) подождать, пока счётчик товаров в корзине обновится
        int expectedQuantity = Integer.parseInt(quantityBeforeAddingProduct) + 1;
        String quantityAfterAddingProduct = Integer.toString(expectedQuantity);
        wait.until(ExpectedConditions.textToBePresentInElement(txtQuantityInCart,quantityAfterAddingProduct));
    }

    @Test
    public void addProductTest() {
        // 4) вернуться на главную страницу, повторить предыдущие шаги ещё два раза, чтобы в общей сложности в корзине было 3 единицы товара
        for (int i = 0; i<=2; i++)
            addProduct();

        // 5) открыть корзину (в правом верхнем углу кликнуть по ссылке Checkout)
        driver.findElement(By.cssSelector("div#cart a.link")).click();

        // 6) удалить все товары из корзины один за другим, после каждого удаления подождать, пока внизу обновится таблица
        WebElement counter =  driver.findElement(By.cssSelector("input[name='quantity']"));
        String currentProductsQuantityInCart = counter.getAttribute("value");
        int changedQuantity = Integer.parseInt(currentProductsQuantityInCart) - 1;

        while (changedQuantity >= 0) {
            String newProductsQuantityInCart = Integer.toString(changedQuantity);
            //decrease number of products
            counter =  driver.findElement(By.cssSelector("input[name='quantity']"));
            cleanElementAndSetValue(counter, newProductsQuantityInCart);
            driver.findElement(By.name("update_cart_item")).click();

             //wait for table to be updated with new number of products or for "No products" msg if 0 products
             if (changedQuantity > 0) {
                 wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("table.dataTable tr:nth-child(2) td:nth-child(1)"), newProductsQuantityInCart));
             }
             else {
               wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("div#checkout-cart-wrapper em"), "There are no items in your cart."));
             }
             changedQuantity--;
        }
    }
}
