import application.driver.factory.WindowsDriver;
import dotnet.DotNetElement;
import dotnet.Int32;
import dotnet.ManagedSpyLib;
import ey.managedspy.RemoteObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import system.reflection.Assembly;

public class DotNetTest {
    WebDriver driver;

    @BeforeClass
    public void before(){
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("applicationPath", System.getProperty("user.dir")+"\\src\\test\\resources\\WindowsFormsApp1.exe");
        capabilities.setCapability("ensureCleanSession", "true");
        driver = new WindowsDriver(capabilities);


    }

    @Test
    public void test(){
        DotNetElement element = new DotNetElement(driver.findElement(By.name("Hey my text changed")));
 
        String name = String.valueOf(element.proxy.GetValue("Name"));
        Assert.assertTrue(name.equals("I am a button"));
        
        //element.getText();
        //element.getName();
        //element.click();
        element.rightclick();
        //element.setTextAs("example text");
      

    }

    @AfterClass
    public void after(){

       // driver.quit();
    }
}
