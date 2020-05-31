import application.driver.factory.WindowsDriver;
import application.element.factory.WindowsElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class WindowOptionsTest {
    private WebDriver driver;
    @BeforeMethod
    public void before(){
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("applicationPath", "C:\\Windows\\System32\\calc.exe");
        capabilities.setCapability("applicationName", "Calculator.exe");
        capabilities.setCapability("ensureCleanSession", "true");
        driver = new WindowsDriver(capabilities);
        WebElement calculatorWindow = new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.name("Calculator")));
        driver.switchTo().frame(calculatorWindow);
    }

    @Test
    public void maximizeWindow(){
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void after(){
        driver.quit();
    }
}
