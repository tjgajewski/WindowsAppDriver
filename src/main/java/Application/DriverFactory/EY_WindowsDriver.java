package Application.DriverFactory;


import Application.ElementFactory.WindowsBy;
import Application.ElementFactory.WindowsElement;
import Infrastructure.Automation.IUIAutomation;
import Infrastructure.Automation.IUIAutomationElement;
import Infrastructure.Utils.Library;
import Infrastructure.Utils.LibraryBuilder;
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
import java.util.List;
import java.util.Set;

public class EY_WindowsDriver extends RemoteWebDriver implements WebDriver, SearchContext {


    private DriverBuilder driverBuilder;
    protected LibraryBuilder libraryBuilder;
    protected IUIAutomation iuiAutomation;
    protected IUIAutomationElement rootElement;
    protected IUIAutomationElement windowElement;
    protected Library iuiAutomationElementLib;
    protected DesiredCapabilities capabilities;

    public EY_WindowsDriver(){
        initializeDriver(new DesiredCapabilities());
    }
    public EY_WindowsDriver(DesiredCapabilities capabilities){
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
    public List<WebElement> findElements(By by) {
        return null;
    }

    @Override
    public WindowsElement findElement(By by) {
        WindowsBy windowsBy = new WindowsBy(by);
        PointerByReference propertyCondition = iuiAutomation.createPropertyCondition(windowsBy.getAccessType(),windowsBy.getAccessName());
        IUIAutomationElement element = windowElement.findFirst(propertyCondition);
        return new WindowsElement(element, libraryBuilder);
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
            PointerByReference frameAttributes =iuiAutomation.createPropertyCondition(by.getAccessType(), by.getAccessName());
            windowElement = rootElement.findFirst(frameAttributes);
            windowElement.setFocus();
            return EY_WindowsDriver.this;
        }

        @Override
        public WebDriver frame(WebElement element) {
            windowElement = ((WindowsElement) element).getIUIAutomationElement();
            return null;
        }

        @Override
        public WebDriver parentFrame() {
            return null;
        }

        @Override
        public EY_WindowsDriver window(String stringBy) {
            WindowsBy by = new WindowsBy(stringBy);
            WindowsBy tagBy = new WindowsBy("tagName", "window");
            PointerByReference windowAttributes = iuiAutomation.createAndCondition(iuiAutomation.createPropertyCondition(by.getAccessType(), by.getAccessName()), iuiAutomation.createPropertyCondition(tagBy.getAccessType(), tagBy.getAccessName()));
            windowElement = rootElement.findFirst(windowAttributes);
            windowElement.setFocus();
            return EY_WindowsDriver.this;
        }

        @Override
        public WebDriver defaultContent() {
            windowElement = rootElement;
            return EY_WindowsDriver.this;
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
