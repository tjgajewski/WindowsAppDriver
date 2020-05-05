import application.driver.Factory.WindowsDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

public class CalculatorTest {

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
    public void addition(){

        driver.findElement(By.name("Five")).click();
        driver.findElement(By.name("Plus")).click();
        driver.findElement(By.name("Three")).click();
        driver.findElement(By.name("Equals")).click();
        String name = driver.findElement(By.id("CalculatorResults")).getAttribute("name");
        Assert.assertEquals(name, "Display is 8", "5 plus 3 should equal 8");

    }

    @Test
    public void subtraction(){

        driver.findElement(By.name("Five")).click();
        driver.findElement(By.name("Minus")).click();
        driver.findElement(By.name("Three")).click();
        driver.findElement(By.name("Equals")).click();
        String name = driver.findElement(By.id("CalculatorResults")).getAttribute("name");
        Assert.assertTrue(name.equals("Display is 2"), "5 minus 3 should equal 2");
    }

    @Test
    public void multiplication(){

        driver.findElement(By.name("Five")).click();
        driver.findElement(By.name("Multiply by")).click();
        driver.findElement(By.name("Three")).click();
        driver.findElement(By.name("Equals")).click();
        String name = driver.findElement(By.id("CalculatorResults")).getAttribute("name");
        Assert.assertTrue(name.equals("Display is 20"), "5 times 3 should equal 20");

    }

    @Test
    public void division(){

        driver.findElement(By.name("Five")).click();
        driver.findElement(By.name("Divide by")).click();
        driver.findElement(By.name("Three")).click();
        driver.findElement(By.name("Equals")).click();
        String name = driver.findElement(By.id("CalculatorResults")).getAttribute("name");
        Assert.assertTrue(name.equals("Display is 1.666666666666667"), "5 divided 3 should equal 1.666666666666667");

    }

    @AfterMethod
    public void after(){
        driver.quit();
    }
}
