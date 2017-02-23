package com.dan.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class WindowTest {

    private WebDriver driver;
    private WebDriverWait wait;

    private void login() {
        driver.get("http://localhost/litecart/admin");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }

    @Before
    public void start() {
        driver = new ChromeDriver();
         driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver,35);
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

    @Test
    public void windowTest(){
        login();

        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        List<WebElement> countries = driver.findElements(By.cssSelector("i.fa-pencil"));
        countries.get(1).click();

        String mainWindow = driver.getWindowHandle();
        Set<String> oldWindows = driver.getWindowHandles();
        List<WebElement> links = driver.findElements(By.cssSelector("i.fa-external-link"));
        for (WebElement link : links) {
            link.click();
            String newWindow = wait.until(thereIsWindowOtherThan(oldWindows));
            driver.switchTo().window(newWindow);
            driver.findElement(By.tagName("body"));
            driver.close();
            driver.switchTo().window(mainWindow);
        }
    }

    public ExpectedCondition<String> thereIsWindowOtherThan(Set<String> oldWindows){
        return new ExpectedCondition<String>() {
            @Override
            public String apply(WebDriver driver) {
                    Set<String> handles = driver.getWindowHandles();
                    handles.removeAll(oldWindows);
                    return handles.size() > 0 ? handles.iterator().next() : null;
            }
        };
    }
}

