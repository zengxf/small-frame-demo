package test;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * 测试谷歌浏览器
 * <p>
 * 原文参考：https://www.yiibai.com/selenium/selenium-webdriver-running-test-on-chrome-browser.html
 * <p>
 * Created by zengxf on 2019-12-09
 */
public class TestChromeDriver {

    public static void main( String[] args ) {
        // declaration and instantiation of objects/variables
        System.setProperty( "webdriver.chrome.driver", "K:\\install\\plugin\\web\\chromedriver.exe" );

        WebDriver driver = new ChromeDriver();

        // Launch Website
        driver.navigate()
                .to( "http://www.yiibai.com/" );

        // Maximize the browser
        driver.manage()
                .window()
                .maximize();

        // Scroll down the webpage by 5000 pixels
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript( "scrollBy(0, 100)" );

        // Click on the Search button
        driver.findElement( By.linkText( "Access教程" ) )
                .click();
    }

}
