package com.dan.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class ProductPage extends Page{
    public ProductPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(name="options[Size]")
    public WebElement drpSize;

    @FindBy(css="span.quantity")
    public WebElement txtQuantityInCart;

    @FindBy(name="add_cart_product")
    public WebElement btnAddToCart;

    public void addProductToCart()
    {
        if (isElementPresent(drpSize)) {
            Select dropdown = new Select(drpSize);
            dropdown.selectByVisibleText("Small");
        }

        String quantityBeforeAddingProduct = txtQuantityInCart.getText();
        btnAddToCart.click();
        // 3) подождать, пока счётчик товаров в корзине обновится
        int expectedQuantity = Integer.parseInt(quantityBeforeAddingProduct) + 1;
        String quantityAfterAddingProduct = Integer.toString(expectedQuantity);
        wait.until(ExpectedConditions.textToBePresentInElement(txtQuantityInCart, quantityAfterAddingProduct));
    }
}
