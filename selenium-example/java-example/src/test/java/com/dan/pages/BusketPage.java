package com.dan.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class BusketPage extends Page {
    public BusketPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get("http://localhost/litecart/en/checkout");
    }

    @FindBy(css="td.item")
    public List<WebElement> productsInTable;

    @FindBy(css="li.shortcut:nth-child(1)")
    public WebElement product;

    @FindBy(name="remove_cart_item")
    public WebElement btnDelete;

    @FindBy(css="div#checkout-cart-wrapper em")
    public WebElement lblNoProducts;


    public void removeAllProductsOneByOne(){
        int numberOfProducts =  productsInTable.size();
        while (isElementPresent(product)) {
            product.click();
            btnDelete.click();
            wait.until(ExpectedConditions.numberOfElementsToBeLessThan(By.cssSelector("td.item"), numberOfProducts));
            numberOfProducts = productsInTable.size();
        }
        //remove last product
        btnDelete.click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("div#checkout-cart-wrapper em"), "There are no items in your cart."));
    }

    public boolean isEmpty(){
        try {
            if (lblNoProducts.getText().contains("There are no items in your cart."))
                return true;
            else
                return false;
        }
        catch (NoSuchElementException ex){
            return false;
        }
    }
}
