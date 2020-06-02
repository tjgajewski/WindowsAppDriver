package application.driver.factory;


import application.element.factory.WindowsBy;
import application.element.factory.WindowsElement;
import com.google.common.collect.ImmutableMap;
import infrastructure.automationapi.IUIAutomation;
import infrastructure.automationapi.IUIAutomationElement;
import infrastructure.automationapi.WindowVisualState;
import infrastructure.automationapi.patterns.WindowPattern;
import infrastructure.automationapi.IUIAutomationElementArray;
import infrastructure.utils.FunctionLibraries;
import com.sun.jna.ptr.PointerByReference;
import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.*;
import org.openqa.selenium.logging.Logs;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.Response;

import javax.imageio.ImageIO;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WindowsDriver extends RemoteWebDriver implements WebDriver, SearchContext{


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
            PointerByReference propertyCondition = iuiAutomation.createMultipleConditions(byList);
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
        List<By>byList = new ArrayList<>();
        String[] stringArray = original.split("(?=\\.)|(?=#)|(?=@)|(?=\\$)");
        By b1 =  By.tagName(stringArray[0]);
        byList.add(b1);
        for(int i = 1; i < stringArray.length; i++){
            String value = stringArray[i];
            By tempBy = null;
            if(value.contains(".")){
                tempBy = By.name(value.replaceAll("\\.", ""));
            }
            if(value.contains("#")){
                tempBy = By.id(value.replaceAll("#", ""));
            }
            if(value.contains("@")){
                tempBy = By.className(value.replaceAll("@", ""));
            }
            if(value.contains("$")){
                tempBy = By.linkText(value.replaceAll("\\$", ""));
            }
            byList.add(tempBy);
        }
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
        new WindowPattern(windowElement).close();
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
        return new WindowsDriver.WindowsDriverOptions();
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

    public class WindowsDriverOptions implements Options {
        protected WindowsDriverOptions() {
        }

        @Beta
        public Logs logs() {
            //TODO: throw an exception or handle this method
            return null;
        }

        public void addCookie(Cookie cookie) {
            //TODO: throw an exception or handle this method
        }

        public void deleteCookieNamed(String name) {
            //TODO: throw an exception or handle this method
        }

        public void deleteCookie(Cookie cookie) {
            this.deleteCookieNamed(cookie.getName());
        }

        public void deleteAllCookies() {
            //TODO: throw an exception or handle this method
        }

        public Set<Cookie> getCookies() {
            //TODO: throw an exception or handle this method
            return null;
        }

        public Cookie getCookieNamed(String name) {
            //TODO: throw an exception or handle this method
            return null;
        }

        public Timeouts timeouts() {
            //TODO: throw an exception or handle this method
            return null;
        }

        public ImeHandler ime() {
            //TODO: throw an exception or handle this method
            return null;
        }

        @Beta
        public Window window() {
            return new WindowsDriver.WindowsDriverOptions.WindowsWindow();
        }

        @Beta
        public class WindowsWindow implements Window {
            Map<String, Object> rawPoint;

            protected WindowsWindow() {
            }

            public void setSize(org.openqa.selenium.Dimension targetSize) {
//            TODO: Create the Windows Driver equivalent of this; refer to RemoteWebDriver class
            }

            public void setPosition(org.openqa.selenium.Point targetPosition) {
//            TODO: Create the Windows Driver equivalent of this; refer to RemoteWebDriver class
            }

            public org.openqa.selenium.Dimension getSize() {
//            TODO: Create the Windows Driver equivalent of this; refer to RemoteWebDriver class
                return null;
            }

            public org.openqa.selenium.Point getPosition() {
//            TODO: Create the Windows Driver equivalent of this; refer to RemoteWebDriver class
                return null;
            }

            public void maximize() {
                new WindowPattern(windowElement).setWindowVisualState(WindowVisualState.MAXIMIZED);
            }

            public void minimize(){
                new WindowPattern(windowElement).setWindowVisualState(WindowVisualState.MINIMIZED);
            }

            public void fullscreen() {
//            TODO: Create the Windows Driver equivalent of this; refer to RemoteWebDriver class
            }
        }


        protected class RemoteTimeouts implements Timeouts {
            protected RemoteTimeouts() {
            }

            public Timeouts implicitlyWait(long time, TimeUnit unit) {
                WindowsDriver.this.execute("setTimeout", ImmutableMap.of("implicit", TimeUnit.MILLISECONDS.convert(time, unit)));
                return this;
            }

            public Timeouts setScriptTimeout(long time, TimeUnit unit) {
                WindowsDriver.this.execute("setTimeout", ImmutableMap.of("script", TimeUnit.MILLISECONDS.convert(time, unit)));
                return this;
            }

            public Timeouts pageLoadTimeout(long time, TimeUnit unit) {
                WindowsDriver.this.execute("setTimeout", ImmutableMap.of("pageLoad", TimeUnit.MILLISECONDS.convert(time, unit)));
                return this;
            }
        }

        protected class RemoteInputMethodManager implements ImeHandler {
            protected RemoteInputMethodManager() {
            }

            public List<String> getAvailableEngines() {
                Response response = WindowsDriver.this.execute("imeGetAvailableEngines");
                return (List) response.getValue();
            }

            public String getActiveEngine() {
                Response response = WindowsDriver.this.execute("imeGetActiveEngine");
                return (String) response.getValue();
            }

            public boolean isActivated() {
                Response response = WindowsDriver.this.execute("imeIsActivated");
                return (Boolean) response.getValue();
            }

            public void deactivate() {
                WindowsDriver.this.execute("imeDeactivate");
            }

            public void activateEngine(String engine) {
                WindowsDriver.this.execute("imeActivateEngine", ImmutableMap.of("engine", engine));
            }
        }
    }
}
