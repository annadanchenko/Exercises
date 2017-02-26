package com.dan.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends Page {
    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    public void open() {
        driver.get("http://localhost/litecart/en/");
    }

    @FindBy(css="div#box-most-popular li.product:first-child")
    public WebElement firstProduct;


    public void openFirstProduct()
    {
        firstProduct.click();
    }



}
