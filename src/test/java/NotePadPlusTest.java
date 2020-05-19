import application.driver.factory.WindowsDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class NotePadPlusTest {

    WebDriver driver;

    @BeforeMethod
    public void before(){
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("applicationPath", "C:\\Program Files\\Notepad++\\notepad++.exe");
        capabilities.setCapability("ensureCleanSession", "true");
        driver = new WindowsDriver(capabilities);
        WebElement notePadWin = new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.className("Notepad++")));
        driver.switchTo().frame(notePadWin);
        driver.findElement(By.className("Scintilla")).sendKeys(Keys.chord(Keys.CONTROL, "n"));
        //new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(By.name("File"))).click();
        //new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(By.name("Print"))).click();
       // driver.findElement(By.name("New")).click();
    }
    @Test
    public void write() throws InterruptedException {
        driver.findElement(By.className("Scintilla")).sendKeys("SEI");
        Thread.sleep(1000);
        String name = driver.findElement(By.className("Scintilla")).getAttribute("name");
        Assert.assertTrue(name.equals("SEI"), name +" is expected to be SEI");
    }

    @Test
    public void chordKeys() throws InterruptedException {
        driver.findElement(By.className("Scintilla")).sendKeys("Delete Me");
        Thread.sleep(1000);
        String name = driver.findElement(By.className("Scintilla")).getAttribute("name");
        Assert.assertTrue(name.equals("Delete Me"));
        driver.findElement(By.className("Scintilla")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
        driver.findElement(By.className("Scintilla")).sendKeys("Hello World");
        Thread.sleep(1000);
        String name2 = driver.findElement(By.className("Scintilla")).getAttribute("name");
        Assert.assertTrue(name2.equals("Hello World"), name + " is expected to be Hello World");
    }

    @AfterMethod
    public void after(){
        driver.quit();
    }
}
