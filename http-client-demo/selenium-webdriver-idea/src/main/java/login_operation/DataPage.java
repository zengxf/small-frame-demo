package login_operation;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by ZXFeng on 2021/6/18.
 */
public class DataPage extends AbstractPage {

    public DataPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void check() {
        String url = driver.getCurrentUrl();
        System.out.println(url);
        System.out.println(url.endsWith("index"));
    }

}
