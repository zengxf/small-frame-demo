package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.Select;

/**
 * 测试各种命令
 * <p>
 * https://www.yiibai.com/selenium/selenium-webdriver-commands.html
 * <p>
 * Created by zengxf on 2019-12-09
 */
public class TestVariousCommand {

    public static void main( String[] args ) {
        // declaration and instantiation of objects/variables
        System.setProperty( "webdriver.gecko.driver", "K:\\install\\plugin\\web\\geckodriver.exe" );
        System.setProperty( "webdriver.firefox.bin", "C:\\Program Files\\Mozilla Firefox\\firefox.exe" );

        // Initialize Gecko Driver using Desired Capabilities Class
        FirefoxOptions options = new FirefoxOptions();
        options.setCapability( "marionette", true );
        WebDriver driver = new FirefoxDriver( options );

        // Launch Website
        driver.navigate()
                .to( "file:///M:/zxf-demo-github/small-frame-demo/http-client-demo/selenium-webdriver-test/index.html" );

        // Fetch the text "This is sample text." and print it on console
        // Use the class name of the div to locate it and then fetch text using getText() method
        String sampleText = driver.findElement( By.className( "col-md-12" ) )
                .getText();
        System.out.println( sampleText );

        // Use the linkText locator method to find the link and perform click using click() method
        driver.findElement( By.linkText( "This is a link" ) )
                .click();

        // 向前导航
        driver.navigate()
                .forward();

        driver.navigate()
                .to( "file:///M:/zxf-demo-github/small-frame-demo/http-client-demo/selenium-webdriver-test/index.html" );

        // Click on the textbox and send value
        driver.findElement( By.id( "fname" ) )
                .sendKeys( "JavaTpoint" );

        // Clear the text written in the textbox
        driver.findElement( By.id( "fname" ) )
                .clear();

        // Click on the Submit button using click() command
        driver.findElement( By.id( "idOfButton" ) )
                .click();

        // Locate the radio button by id and check it using click() function
        driver.findElement( By.id( "male" ) )
                .click();

        // Locate the checkbox by cssSelector and check it using click() function
        driver.findElement( By.cssSelector( "input.Automation" ) )
                .click();

        // Use Select class for selecting value from dropdown
        Select dropdown = new Select( driver.findElement( By.id( "testingDropdown" ) ) );
        dropdown.selectByVisibleText( "Automation Testing" );

        // Close the Browser
        driver.close();
    }

}
