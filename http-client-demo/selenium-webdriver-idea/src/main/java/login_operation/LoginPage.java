package login_operation;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.Scanner;

/**
 * Created by ZXFeng on 2021/6/18.
 */
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

    public DataPage login(String nameV, String pwdV) {
        username.sendKeys(nameV);
        password.sendKeys(pwdV);
        this.inputCode();
        button.click();
        try {
            driver.wait(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new DataPage(super.driver);
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
