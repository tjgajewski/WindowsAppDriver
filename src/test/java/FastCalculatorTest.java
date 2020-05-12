import application.driver.factory.WindowsDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class FastCalculatorTest {

    WebDriver driver;

    @BeforeClass
    public void before(){
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("applicationPath", "C:\\Windows\\System32\\calc.exe");
        capabilities.setCapability("applicationName", "Calculator.exe");
        capabilities.setCapability("ensureCleanSession", "true");
        driver = new WindowsDriver(capabilities);
        driver.switchTo().frame(new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.name("Calculator"))));

    }

    @Test
    public void addition(){

        driver.findElement(By.name("Five")).click();
        driver.findElement(By.name("Plus")).click();
        driver.findElement(By.name("Three")).click();
        driver.findElement(By.name("Equals")).click();
        String name = driver.findElement(By.id("CalculatorResults")).getAttribute("name");
        Assert.assertTrue(name.equals("Display is 8"), "5 plus 3 should equal 8");

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
        Assert.assertFalse(name.equals("Display is 20"), "5 times 3 should equal 20");

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

    @AfterClass
    public void after(){
        driver.quit();
    }
}
