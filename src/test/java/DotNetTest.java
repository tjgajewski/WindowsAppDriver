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

        //Load the Remote Object Assembly
        Assembly assembly = Assembly.Load("System.Windows.Forms, Version=4.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089");

        //Create the mouse buttons parameter
        //URL: https://docs.microsoft.com/en-us/dotnet/api/system.windows.forms.mousebuttons?view=netcore-3.1
        RemoteObject mouseButtons = new RemoteObject();
        mouseButtons.settypeName("System.Windows.Forms.MouseButtons");
        mouseButtons.setassembly(assembly.getLocation());
        mouseButtons.setgetType("property");
        mouseButtons.setname("Right");

        //Create the Mouse Arguments Parameter
        //URL: https://docs.microsoft.com/en-us/dotnet/api/system.windows.forms.mouseeventargs?view=netcore-3.1
        RemoteObject mouseEventArgs = new RemoteObject();
        mouseEventArgs.settypeName("System.Windows.Forms.MouseEventArgs");
        mouseEventArgs.setassembly(assembly.getLocation());
        mouseEventArgs.setparams(new system.Object[] {mouseButtons, Int32.parse(1), Int32.parse(33), Int32.parse(12), Int32.parse(1)});
        element.proxy.Invoke("OnMouseDown", new system.Object[] {mouseEventArgs});


    }

    @AfterClass
    public void after(){

        //driver.quit();
    }
}
