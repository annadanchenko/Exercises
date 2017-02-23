package com.dan.tests;

import junit.framework.AssertionFailedError;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;

public class BrowserLogTest {

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

    public void openInNewTab(WebElement link){
        Actions newTab = new Actions(driver);
        newTab.keyDown(Keys.CONTROL).keyDown(Keys.SHIFT).click(link).keyUp(Keys.CONTROL).keyUp(Keys.SHIFT).build().perform();
       // Thread.sleep(5000);

  /*      //handle windows change
        String base = getMyDriver().getWindowHandle();
        Set<String> set = getMyDriver().getWindowHandles();

        set.remove(base);
        assert set.size() == 1;
        getMyDriver().switchTo().window((String) set.toArray()[0]);

//close the window and sitch back to the base tab
        getMyDriver().close();
        getMyDriver().switchTo().window(base);*/
    }




    @Test
    public void logTest(){
        login();

        driver.get("http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1");
        String mainWindow = driver.getWindowHandle();
        Set<String> oldWindows = driver.getWindowHandles();

        List<WebElement> links = driver.findElements(By.xpath("//table[contains(@class, 'dataTable')]/tbody/tr/td[3]/a"));
        for (WebElement link : links) {
            if (link.getAttribute("href").contains("product_id")) {
                openInNewTab(link);
                String newWindow = wait.until(thereIsWindowOtherThan(oldWindows));
                driver.switchTo().window(newWindow);
                driver.findElement(By.tagName("body"));
                 List<LogEntry> logs = driver.manage().logs().get("browser").getAll();
                 if (logs.size() > 0){
                     for (LogEntry l : logs) {
                         System.out.println(l);
                     }
                     Assert.fail("Browser log contains some entries");
                 }
               
                driver.close();
                driver.switchTo().window(mainWindow);
            }
        }



        for (LogEntry l : driver.manage().logs().get("browser").getAll()) {
            System.out.println(l);
        }
      /*  List<WebElement> countries = driver.findElements(By.cssSelector("i.fa-pencil"));
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
        }*/
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

