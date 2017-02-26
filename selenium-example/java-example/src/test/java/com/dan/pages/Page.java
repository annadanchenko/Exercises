package com.dan.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Page {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public Page(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 10);
    }

    public boolean isElementPresent(By locator) {
        try {
            return driver.findElements(locator).size() > 0;
        } catch (InvalidSelectorException ex) {
            throw ex;
        }
    }

    public boolean isElementPresent(WebElement element) {
        try {
           // return driver.findElements(locator).size() > 0;
            element.getTagName();
            return true;

        } catch (NoSuchElementException ex) {
            return false;
        }
    }
}
