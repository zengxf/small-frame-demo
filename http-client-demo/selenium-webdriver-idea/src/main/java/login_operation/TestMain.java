package login_operation;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Created by ZXFeng on 2021/6/18.
 */
@Slf4j
public class TestMain {

    public static void main(String[] arr) {
        System.setProperty("webdriver.gecko.driver", "K:/install/plugin/web/geckodriver.exe");
        System.setProperty("webdriver.firefox.bin", "C:/Program Files/Mozilla Firefox/firefox.exe");

        WebDriver driver = new FirefoxDriver();
        driver.get("http://localhost:9999/#/login");

        try {
            LoginPage login = new LoginPage(driver);
            HomePage home = login.login("admin", "admin-fedkGW8d");
            home.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        driver.quit();
    }

}
