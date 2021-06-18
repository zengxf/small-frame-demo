package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * 测试百度搜索
 * <p>
 * 原文参考：https://www.yiibai.com/selenium/selenium-webdriver-first-test-case.html
 * <p>
 * Created by zengxf on 2019-12-09
 */
public class TestBaiduSearch {

    public static void main( String[] args ) {
        // declaration and instantiation of objects/variables
        System.setProperty( "webdriver.gecko.driver", "K:\\install\\plugin\\web\\geckodriver.exe" );
        System.setProperty( "webdriver.firefox.bin", "C:\\Program Files\\Mozilla Firefox\\firefox.exe" );

        WebDriver driver = new FirefoxDriver();

        // Launch website
        // driver.navigate().to("http://www.baidu.com/");
        driver.get( "http://www.baidu.com/" );
        // driver.manage().window().maximize();
        String titile = driver.getTitle();

        System.out.println( "title is => " + titile );

        // Click on the search text box and send value
        driver.findElement( By.id( "kw" ) )
                .sendKeys( "易百教程" );

        // Click on the search button
        driver.findElement( By.id( "su" ) )
                .click();

        try {
            Thread.sleep( 3000 );
        } catch ( InterruptedException e ) {
            e.printStackTrace();
        }
        driver.quit(); // 自动退出
    }

}
