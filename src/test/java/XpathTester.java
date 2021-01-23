import application.driver.factory.WindowsDriver;
import application.element.factory.WindowsElement;
import infrastructure.xpath.ElementQueryable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import xpath.parser.CommandList;
import xpath.parser.Queryable;
import xpath.parser.Xpath;

import java.util.List;

public class XpathTester {

    public static void main(String[] args){
        String xpath = "//*[@id='NumberPad']//*[@name='Five']";
        String xpath23 = "//button[@id='Minimize']";
        String xpath33 = "//*[@id='NumberPad']//button[4]";
        String xpath3 = "//*[@id='NumberPad']//*[@name='Five']";
        String xpath4 = "//*[@id='NumberPad']//*[@name='Five']";
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("applicationPath", "C:\\Windows\\System32\\calc.exe");
        capabilities.setCapability("applicationName", "Calculator.exe");
        capabilities.setCapability("ensureCleanSession", "true");
        WindowsDriver driver = new WindowsDriver(capabilities);
        WebElement calculatorWindow = new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.name("Calculator")));
        driver.switchTo().frame(calculatorWindow);
        driver.findElement(By.id("NumberPad"));
        WindowsElement e = driver.findElement(By.xpath(xpath));
        Xpath xpath2 = new Xpath(new ElementQueryable(new WindowsElement(driver.getWindowElement(),driver), xpath33));
        List<Queryable> queryableList=xpath2.compile(xpath33).execute();
        System.out.println(queryableList.size());
        System.out.println(((ElementQueryable) queryableList.get(0)).getElement().getAttribute("name"));
    }
}
