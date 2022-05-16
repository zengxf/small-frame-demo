package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * 测试易百搜索
 * <p>
 * 原文参考：https://www.yiibai.com/selenium/selenium-webdriver-running-test-on-firefox-browser-gecko-driver.html
 * <p>
 * Created by zengxf on 2019-12-09
 */
public class TestFirefoxDriver {

    public static void main(String[] args) {
        // declaration and instantiation of objects/variables
        System.setProperty("webdriver.gecko.driver", "C:/Install/Others/web-driver/geckodriver-v0.31.0-win64.exe");
        System.setProperty("webdriver.firefox.bin", "C:/Program Files/Mozilla Firefox/firefox.exe");

        WebDriver driver = new FirefoxDriver();

        // Launch Website
        driver.navigate()
                .to("http://www.yiibai.com/");

        // Click on the Custom Search text box and send value
        driver.findElement(By.name("kw"))
                .sendKeys("java教程");
        driver.findElement(By.id("submit")).click();

        // Click on the Search button
        driver.findElement(By.className("article-list-item-txt")).click();
    }

}
