import application.actions.factory.WindowsActions;
import application.driver.factory.WindowsDriver;
import application.element.factory.WindowsBy;
import application.element.factory.WindowsElement;
import com.sun.jna.platform.WindowUtils;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.ptr.PointerByReference;
import infrastructure.automationapi.IUIAutomation;
import infrastructure.automationapi.IUIAutomationElement;
import infrastructure.automationapi.IUIAutomationTreeWalker;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.awt.*;
import java.util.List;

public class CalculatorTest {

    private WindowsDriver driver;

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
    public void addition() throws InterruptedException {




        for(int i = 0; i < 10 ; i++){
            Actions action = new Actions(driver);
            action.moveToElement(driver.findElement(By.id("num"+i+"Button"))).perform();
            Thread.sleep(1000);
        }

        List<WebElement> elements = driver.findElements(By.xpath("//*[@id='NumberPad']//button"));
        for(WebElement element: elements){
            System.out.println(element.getLocation());
            Actions action = new Actions(driver);
            action.moveToElement(element).perform();
            Thread.sleep(1000);
        }


WindowsElement element = driver.findElement(By.xpath("/window/window/group/group"));
String x = element.getAbsXpath();
        driver.getWindowElement().getCurrentPropertyValue("hwnd");
        List<WindowsElement> siblings = element.getParent().getAllChildrenElements(By.tagName(element.getTagName()));
        driver.findElement(By.xpath("/window/window/group/group")).getAttribute("name");
        driver.findElement(By.cssSelector("button.Five")).click();
        driver.findElement(By.name("Open Navigation")).click();
        driver.findElement(By.name("Programmer Calculator")).isDisplayed();
        driver.findElement(By.name("Programmer Calculator")).getSize();
        driver.findElement(By.name("Programmer Calculator")).getRect();
        driver.findElement(By.cssSelector("button.Plus")).click();
        driver.findElement(By.cssSelector("button.Three")).click();
        driver.findElement(By.cssSelector("button.Equals")).click();
        String name = driver.findElement(By.id("CalculatorResults")).getAttribute("name");
        Assert.assertEquals(name, "Display is 8", "5 plus 3 should equal 8");

    }

    private void compileXml(StringBuilder front, StringBuilder endTags, WindowsElement element){
        List<WindowsElement> children = null;
try {
    children = element.getAllChildrenElements();
}
catch (Error e){
    System.out.println("puase");
}
        
        
        for(WindowsElement child : children){
            String tag = child.getTagName();
            front.append("<"+tag);
            appendAttributeToXml(front, child, "name");
            appendAttributeToXml(front, child, "id");
            appendAttributeToXml(front, child, "classname");
            //appendAttributeToXml(front, child, "size");
            appendAttributeToXml(front, child, "frameworkid");
           // appendAttributeToXml(front, child, "hwnd");
            appendAttributeToXml(front, child, "text");
            if(children.size()>0){
                compileXml(front, endTags, child);
            }
            front.append("></"+tag+">");
        }
    }

    private void appendAttributeToXml(StringBuilder sb, WindowsElement element, String propertyName){
        String value = element.getAttribute(propertyName);
        if(!value.equals("")&&!value.equals("null")){
            sb.append(" "+propertyName+"=\""+value+"\"");
        }
    }

    public WindowsElement descendentElementFromPoint(WindowsElement element, Point point){
        for(WindowsElement child : element.getAllDescendentElements()){
            if(child.getWinDefRect().toRectangle().contains(point)&&child.getAllChildrenElements().size()==0){
               return child;
            }
        }
        return element;
    }

    @Test
    public void addition2(){
        Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(By.name("Five")));
        action.sendKeys("hello");
        action.perform();
        driver.findElement(By.name("Minus")).click();
        driver.findElement(By.name("Three")).click();
        driver.findElement(By.name("Equals")).click();
        String name = driver.findElement(By.id("CalculatorResults")).getAttribute("name");
        Assert.assertTrue(name.equals("Display is 2"), "5 minus 3 should equal 2");

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

    @Test
    public void debugging()
    {//BoundingRectangle	[l=759,t=618,r=852,b=691]BoundingRectangle	[l=759,t=694,r=852,b=767]BoundingRectangle	[l=759,t=618,r=852,b=691]


       // String s =driver.findElement(By.cssSelector("button.Eight")).getAttribute("BoundingRectangle");
        //driver.findElement(By.xpath("[@BoundingRectangle=759,618,852,691")).click();
        driver.findElement(By.cssSelector("button.Five")).click();
        driver.findElementByRect("button.[l=759,t=694,r=852,b=767]");
        driver.findElement(By.xpath("[@name='Five']/>/>")).click();
        driver.findElement(By.xpath("[@name='Five']/<")).click();
    }
    @AfterMethod
    public void after(){
        driver.quit();
    }
}
