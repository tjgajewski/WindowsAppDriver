import application.driver.Factory.WindowsDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class WindowsDriverWaitTest {
    WindowsDriver driver;

    @BeforeMethod
    public void before() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("applicationPath", "C:\\Program Files (x86)\\SAP\\FrontEnd\\SAPgui\\saplogon.exe");
        driver = new WindowsDriver(capabilities);
    }

    @Test
    public void presence(){
        WebElement sapWindow = new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.name("SAP Logon 750")));
        driver.switchTo().frame(sapWindow);
        driver.findElement(By.id("1091")).sendKeys("This is a test");
        String text = driver.findElement(By.id("1091")).getText();
        Assert.assertTrue(text.equals("This is a test"), "SAP Logon receives text!");

    }

    @Test
    public void viability(){
        WebElement sapWindow = new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.name("SAP Logon 750")));
        driver.switchTo().frame(sapWindow);
        driver.findElement(By.id("1091")).sendKeys("This is a test");
        String text = driver.findElement(By.id("1091")).getText();
        Assert.assertTrue(text.equals("This is a test"), "SAP Logon receives text!");

    }

    @Test
    public void enabled(){
        WebElement sapWindow = new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(By.name("SAP Logon 750")));
        driver.switchTo().frame(sapWindow);
        driver.findElement(By.id("1091")).sendKeys("This is a test");
        String text = driver.findElement(By.id("1091")).getText();
        Assert.assertTrue(text.equals("This is a test"), "SAP Logon receives text!");
    }


    @AfterMethod
    public void after(){
        driver.quit();
    }
}
