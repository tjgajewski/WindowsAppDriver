import application.driver.factory.WindowsDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class NotePadTest {

    WebDriver driver;

    @BeforeClass
    public void before(){
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("applicationPath", "C:\\WINDOWS\\system32\\notepad.exe");


        WebDriver driver = new WindowsDriver();
        driver.findElement(By.name("Text Editor")).sendKeys("Hello World");
        String text = driver.findElement(By.name("Text Editor")).getText();
        Assert.assertTrue(text.equals("Hello World"), "Notepad receives text!");

    }

    @Test
    public void testLinkTextBy(){
        driver.findElement(By.linkText("hello")).sendKeys("Success");
    }

    @Test
    public void write() {
        driver.findElement(By.name("Text Editor")).sendKeys("Hello World");
        Actions action = new Actions(driver);
        action.contextClick(driver.findElement(By.name("Text Editor")));
        action.perform();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String text = driver.findElement(By.name("Text Editor")).getText();
        Assert.assertTrue(text.equals("Hello World"), "Notepad receives text!");
    }

    @AfterClass
    public void after(){
        driver.quit();
    }
}

