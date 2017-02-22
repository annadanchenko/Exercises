package com.dan.tests;

import com.dan.framework.base.TestBase;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class GeoZonesTest extends TestBase {


   private void login() {
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }

    void checkListIsSorted(List<WebElement> elements){
        int size = elements.size();
        int compareResult = 0;
        String current = "";
        String previous = "";

        for (int i = 0; i < size; i++) {
            current = elements.get(i).getText();
            if (i>0)
            {
                compareResult = current.compareTo(previous);
                Assert.assertTrue(compareResult>=0);
            }
            previous = current;
           // System.out.println(elements.get(i).getText());
        }
    }

    void checkGeoLinks(List<String> links, By locator){
        //for each page - open it, locate list of elements with locator and check that located list is sorted
        for (String link : links) {
            driver.get(link);
            List<WebElement> elements = driver.findElements(locator);
            checkListIsSorted(elements);
        }
    }


   /*
   1) на странице http://localhost/litecart/admin/?app=countries&doc=countries
    а) проверить, что страны расположены в алфавитном порядке
    б) для тех стран, у которых количество зон отлично от нуля -- открыть страницу этой страны и там проверить, что зоны расположены в алфавитном порядке
    */
  //  @Test
    public void geoZonesTest1() {
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        login();

        List<WebElement> countries = driver.findElements(By.xpath("//table[contains(@class, 'dataTable')]/tbody/tr/td[5]"));
        checkListIsSorted(countries);

        List<WebElement> zones = driver.findElements(By.xpath("//table[contains(@class, 'dataTable')]/tbody/tr/td[6]"));
        List<String> links = new ArrayList<String>();
        for (int i = 0; i < zones.size(); i++) {
            if (!zones.get(i).getText().equals("0")) {
                String link = countries.get(i).findElement(By.tagName("a")).getAttribute("href");
                links.add(link);
            }
        }
        checkGeoLinks(links, By.xpath("//table[contains(@class, 'dataTable')]/tbody/tr/td[3]/input[@type='hidden']"));
    }


    /*
    2) на странице http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones
    зайти в каждую из стран и проверить, что зоны расположены в алфавитном порядке
     */
   // @Test
    public void geoZonesTest2() {
        //open page
        driver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
        login();

        //get links to pages with geozones
        List<WebElement> countries = driver.findElements(By.xpath("//table[contains(@class, 'dataTable')]/tbody/tr/td[3]/a"));
        List<String> links = new ArrayList<String>();
        for (int i = 0; i < countries.size(); i++) {
            String link = countries.get(i).getAttribute("href");
            links.add(link);
        }
        checkGeoLinks(links, By.xpath("//table[contains(@class, 'dataTable')]/tbody/tr/td[3]/select/option[@selected='selected']"));
    }


}
