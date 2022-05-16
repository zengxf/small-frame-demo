package login_operation;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Scanner;

/**
 * Created by ZXFeng on 2021/6/18.
 */
@Slf4j
public class LoginPage extends AbstractPage {

    @FindBy(how = How.XPATH, using = "//*[@id=\"app\"]/div/form/div[1]/div/div/input")
    private WebElement username;
    @FindBy(xpath = "//*[@id=\"app\"]/div/form/div[2]/div/div/input")
    private WebElement password;
    @FindBy(xpath = "//*[@id=\"app\"]/div/form/div[3]/div/div[1]/input")
    private WebElement code;
    @FindBy(tagName = "button")
    private WebElement button;

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public HomePage login(String nameV, String pwdV) {
        username.sendKeys(nameV);
        password.sendKeys(pwdV);
        this.inputCode();
        button.click();
        this.waitHome();
        return new HomePage(super.driver);
    }

    private void waitHome() {
        long start = System.currentTimeMillis();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // Max 15s
        wait.until(ExpectedConditions.urlContains("index"));
        long end = System.currentTimeMillis();
        log.info("等待首页耗时：[{}] ms", end - start);
    }

    private void inputCode() {
        System.out.println();
        System.out.print("请输入验证码：");
        Scanner sc = new Scanner(System.in);
        String codeV = sc.next();
        System.out.println("验证码是：[" + codeV + "]");
        code.sendKeys(codeV);
        System.out.println();
    }

}
