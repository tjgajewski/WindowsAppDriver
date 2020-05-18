package application.driver.factory;


import application.element.factory.WindowsBy;
import application.element.factory.WindowsElement;
import infrastructure.automationapi.IUIAutomation;
import infrastructure.automationapi.IUIAutomationElement;
import infrastructure.automationapi.patterns.IUIAutomationElementArray;
import infrastructure.utils.FunctionLibraries;
import com.sun.jna.ptr.PointerByReference;
import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import javax.imageio.ImageIO;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class WindowsDriver extends RemoteWebDriver implements WebDriver, SearchContext {


    private application.driver.factory.DriverBuilder driverBuilder;
    protected FunctionLibraries libraryBuilder;
    protected IUIAutomation iuiAutomation;
    protected IUIAutomationElement rootElement;
    protected IUIAutomationElement windowElement;
    protected FunctionLibraries iuiAutomationElementLib;
    protected DesiredCapabilities capabilities;


    public WindowsDriver(){
        initializeDriver(new DesiredCapabilities());
    }

    public WindowsDriver(DesiredCapabilities capabilities){
        initializeDriver(capabilities);
    }
    private void initializeDriver(DesiredCapabilities capabilities){
        driverBuilder = new DriverBuilder(this);
        driverBuilder.configureCapabilities(capabilities);
        driverBuilder.connectToInterface();
        driverBuilder.build();
    }



    @Override
    public void get(String s) {

    }

    @Override
    public String getCurrentUrl() {
        return null;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public WindowsElement findElement(By by) {
        WindowsBy windowsBy = new WindowsBy(by);
       IUIAutomationElement element = null;
        if(windowsBy.getAttribute().equalsIgnoreCase("By.cssSelector")){
            List<By> byList = splitCssSelectorBy(by);
            WindowsBy windowsBy1 = new WindowsBy(byList.get(0));
            WindowsBy windowsBy2 = new WindowsBy(byList.get(1));
            PointerByReference propertyCondition = iuiAutomation.createAndCondition(iuiAutomation.createPropertyCondition(windowsBy1), iuiAutomation.createPropertyCondition(windowsBy2));
            element = windowElement.findFirst(propertyCondition, windowsBy);
        }
        else{
            PointerByReference propertyCondition = iuiAutomation.createPropertyCondition(windowsBy.getAttributeIndex(), windowsBy.getAttributeValue());
            element = windowElement.findFirst(propertyCondition, windowsBy);
        }
        return new WindowsElement(element);
    }

    public List<By> splitCssSelectorBy(By by){
        String original = by.toString().split(":")[1];
        original = original.replaceAll(" ", "");
        List<String> stringList = new ArrayList<>();
        List<By>byList = new ArrayList<>();
        By by1;
        By by2;
        if(original.contains(".")){
            stringList = Arrays.asList(original.split("\\."));
            by2 = By.name(stringList.get(1));
        }
        else if(original.contains("#")){
            stringList = Arrays.asList(original.split("#"));
            by2 = By.id(stringList.get(1));
        }
        else if(original.contains("@")){
            stringList = Arrays.asList(original.split("@"));
            by2 = By.className(stringList.get(1));
        }
        else if(original.contains("$")){
            stringList = Arrays.asList(original.split("@"));
            by2 = By.linkText(stringList.get(1));
        }
        else{
            by2 = null;
        }
        by1 = By.tagName(stringList.get(0));
        byList.add(by1);
        byList.add(by2);
        return byList;
    }


    @Override
    public List<WebElement> findElements(By by) {
        WindowsBy windowsBy = new WindowsBy(by);
        PointerByReference propertyCondition = iuiAutomation.createPropertyCondition(windowsBy.getAttributeIndex(), windowsBy.getAttributeValue());
        IUIAutomationElementArray elements = windowElement.findAll(propertyCondition, windowsBy);
        List<WebElement> elementsList = new ArrayList<>();
        for(int i = 0; i < elements.getLength(); i++){
            WindowsElement currentElement = new WindowsElement(elements.getElement(i));
            elementsList.add(currentElement);
        }
        return elementsList;
    }

    @Override
    public String getPageSource() {
        return null;
    }

    @Override
    public void close() {
        driverBuilder.closeApplication();
    }

    @Override
    public void quit() {
        driverBuilder.closeApplication();
    }

    @Override
    public Set<String> getWindowHandles() {
        return null;
    }

    @Override
    public String getWindowHandle() {
        return null;
    }

    @Override
    public TargetLocator switchTo() {
        return new WindowsTargetLocator();
    }

    @Override
    public Navigation navigate() {
        return null;
    }

    @Override
    public Options manage() {
        return null;
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> outputType) throws WebDriverException {
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        java.awt.Rectangle screenRect = new Rectangle(new Point(0,0),screenSize);
        BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
        String base64EncodedPng = null;
        try {
            base64EncodedPng = encodeFileToBase64Binary(screenFullImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputType.convertFromBase64Png(base64EncodedPng);
    }

    private String encodeFileToBase64Binary(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();
        return new String(Base64.encodeBase64(imageInByte));
    }

    public class WindowsTargetLocator implements WebDriver.TargetLocator {
        @Override
        public WebDriver frame(int i) {
            return null;
        }

        @Override
        public WebDriver frame(String stringBy) {
            WindowsBy by = new WindowsBy(stringBy);
            PointerByReference frameAttributes =iuiAutomation.createPropertyCondition(by.getAttributeIndex(), by.getAttributeValue());
            windowElement = rootElement.findFirst(frameAttributes, by);
            windowElement.setFocus();
            return WindowsDriver.this;
        }

        @Override
        public WebDriver frame(WebElement element) {
            windowElement = ((WindowsElement) element).getIUIAutomationElement();
            windowElement.setFocus();
            return null;
        }

        @Override
        public WebDriver parentFrame() {
            return null;
        }

        @Override
        public WindowsDriver window(String stringBy) {
            WindowsBy by = new WindowsBy(stringBy);
            WindowsBy tagBy = new WindowsBy("tagName", "window");
            PointerByReference windowAttributes = iuiAutomation.createAndCondition(iuiAutomation.createPropertyCondition(by.getAttributeIndex(), by.getAttributeValue()), iuiAutomation.createPropertyCondition(tagBy.getAttributeIndex(), tagBy.getAttributeValue()));
            windowElement = rootElement.findFirst(windowAttributes, by);
            windowElement.setFocus();
            return WindowsDriver.this;
        }

        @Override
        public WebDriver defaultContent() {
            windowElement = rootElement;
            return WindowsDriver.this;
        }

        @Override
        public WebElement activeElement() {
            return null;
        }

        @Override
        public Alert alert() {
            return null;
        }


    }
}
