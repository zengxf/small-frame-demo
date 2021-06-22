package login_operation;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Created by ZXFeng on 2021/6/18.
 */
@Slf4j
public class HomePage extends AbstractPage {

    @FindBy(xpath = "//*[@id=\"app\"]/div/div[1]/div[2]/div[1]/div/ul/div[2]/li/div/span")
    private WebElement userManageSpan;
    private final WebDriverWait wait;

    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 30);
    }

    public void click() {
        this.waitClickable();
        userManageSpan.click();

        WebElement userWealthA = this.waitElement(By.linkText("用户资产"));
        userWealthA.click();

        this.sendAjax();

        this.printTable();
    }

    private void printTable() {
        WebElement table1 = this.waitElement(By.xpath("//*[@id=\"app\"]/div/div[2]/section/div/div[1]/div[2]/table"));
        WebElement tr = table1.findElement(By.tagName("TR"));
        List<WebElement> ths = tr.findElements(By.tagName("th"));
        ths.forEach(th -> log.info("th: [{}]", th.getText()));

        By dataTableBy = By.xpath("//*[@id=\"app\"]/div/div[2]/section/div/div[1]/div[4]/div[2]/table");
        WebElement table2 = this.waitElement(dataTableBy);
        List<WebElement> trs = table2.findElements(By.tagName("tr"));
        trs.forEach(dataTR -> {
            List<WebElement> tds = dataTR.findElements(By.tagName("td"));
            tds.forEach(td -> {
                WebElement div = td.findElement(By.tagName("div"));
                System.out.print(div.getText() + "\t");
            });
            System.out.println();
        });
    }

    private void sendAjax() {
        // 开启开发者模式，方便观察请求
        // 下面这些方式都不行，不如打断点，再 F12
//        Actions actions = new Actions(driver);
//        actions.sendKeys(Keys.F12).perform();
//        driver.findElement(By.tagName("body")).sendKeys(Keys.F12);

        Cookie tokenCookie = driver.manage().getCookieNamed("Admin-Token");
        String token = tokenCookie.getValue();
        String authToken = "Bearer " + token;

        JavascriptExecutor jse = (JavascriptExecutor) driver;
        try {
            String res = (String) jse.executeScript(
                    "var xmlHttp = new XMLHttpRequest();\n" +
                            "xmlHttp.open(\"GET\",\"http://localhost:9999/dev-api/user/wealth/list?pageNum=1&pageSize=10\",false);\n" +
                            "xmlHttp.setRequestHeader(\"Content-type\",\"application/json;charset=utf-8\");\n" +  // 表单提交的头部信息
                            "xmlHttp.setRequestHeader(\"Authorization\",\"" + authToken + "\");\n" +  // 自定义请求头 -- Token 头
                            "xmlHttp.send();\n" +   // 表单数据
                            "return xmlHttp.responseText;");
            System.out.println("\nJSON res: " + res + "\n");
        } catch (Exception e) {
            log.error("执行 JS Ajax 出错！", e);
        }
    }

    private void waitClickable() {
        long start = System.currentTimeMillis();
        wait.until(ExpectedConditions.elementToBeClickable(userManageSpan)); // 这个不一定准
        long end = System.currentTimeMillis();
        long waits = end - start;
        if (waits < 5000L) {
            try {
                Thread.sleep(5000L - waits);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        log.info("等待可点击耗时：[{}] ms", waits);
    }

    private WebElement waitElement(By by) {
        return this.waitElement(by, 0L);
    }

    private WebElement waitElement(By by, long base) {
        long start = System.currentTimeMillis();
        WebElement element = wait.until(webDriver -> webDriver.findElement(by));
        long end = System.currentTimeMillis();
        long waits = end - start;
        log.info("等待获取元素耗时：[{}] ms", waits);
        if (waits < base) {
            try {
                Thread.sleep(base - waits);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return element;
    }

}
