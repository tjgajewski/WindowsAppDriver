package application.driver.factory;


import application.driver.command.WindowsDriverCommands;
import application.element.factory.ElementHelpers;
import application.element.factory.WindowsBy;
import application.element.factory.WindowsElement;
import com.google.common.collect.ImmutableMap;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.ptr.PointerByReference;
import infrastructure.automationapi.*;
import infrastructure.automationapi.patterns.WindowPattern;
import infrastructure.xpath.ElementQueryable;
import org.apache.commons.codec.binary.Base64;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.logging.Logs;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.Response;
import xpath.parser.Queryable;
import xpath.parser.Xpath;

import javax.imageio.ImageIO;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class WindowsDriver extends RemoteWebDriver implements WebDriver, SearchContext{


    private application.driver.factory.DriverBuilder driverBuilder;

    public IUIAutomation getIuiAutomation() {
        return iuiAutomation;
    }

    public IUIAutomationElement getWindowElement() {
        return windowElement;
    }

    protected IUIAutomation iuiAutomation;

    public WindowsElement getRootElement() {
        return new WindowsElement(rootElement,this);
    }

    protected IUIAutomationElement rootElement;
    protected IUIAutomationElement windowElement;
    protected DesiredCapabilities capabilities;
    protected WindowsDriverCommands command;


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
        return findElement(new WindowsBy(by));
    }

    public WindowsElement findElement(WindowsBy windowsBy) {
        if(windowsBy.getAttribute().equals("xpath")){
            Xpath xpath2 = new Xpath(new ElementQueryable(new WindowsElement(windowElement,this), windowsBy.getAttributeValue().toString()));
            List<Queryable> queryableList=xpath2.compile(windowsBy.getAttributeValue().toString()).execute();
            return ((ElementQueryable) queryableList.get(0)).getElement();
        }
        else {
            return new WindowsElement(windowsBy, iuiAutomation, windowElement, this);
        }
    }

    public WindowsElement getNextSibling(By by){
        IUIAutomationTreeWalker treeWalker = iuiAutomation.getTreeWalker();
        IUIAutomationElement firstElement =  ElementHelpers.getIUIAutomationElement(by, iuiAutomation, windowElement, this);
        IUIAutomationElement targetElement = treeWalker.getNextSiblingElement(firstElement);
        WindowsElement windowsElement = new WindowsElement(targetElement, this);
        return windowsElement;
    }
    @Override
    public List<WebElement> findElements(By by) {
        WindowsBy windowsBy = new WindowsBy(by);
        List<WebElement> elementsList = new ArrayList<>();
        if(windowsBy.getAttribute().equals("xpath")){
            Xpath xpath2 = new Xpath(new ElementQueryable(new WindowsElement(windowElement,this), windowsBy.getAttributeValue().toString()));
            List<Queryable> queryableList=xpath2.compile(windowsBy.getAttributeValue().toString()).execute();
            for(Queryable queryable:queryableList){
                elementsList.add(((ElementQueryable) queryable).getElement());
            }
        }
        else {
            PointerByReference propertyCondition = iuiAutomation.createPropertyCondition(windowsBy.getAttributeIndex(), windowsBy.getAttributeValue().toString());
            IUIAutomationElementArray elements = windowElement.findAll(propertyCondition, windowsBy);

            for (int i = 0; i < elements.getLength(); i++) {
                WindowsElement currentElement = new WindowsElement(elements.getElement(i), this);
                elementsList.add(currentElement);
            }
        }
        return elementsList;
    }

    public WindowsElement elementFromPoint(WinDef.POINT point){
        PointerByReference pointerToElement = new PointerByReference();
        iuiAutomation.elementFromPoint(point, pointerToElement);
        return new WindowsElement(new IUIAutomationElement(pointerToElement), this);

    }

    public WindowsElement getFocusedElement(){
        PointerByReference pointerToElement = new PointerByReference();
        iuiAutomation.getFocusedElement(pointerToElement);
        return new WindowsElement(new IUIAutomationElement(pointerToElement), this);

    }

    public Element xml;

    public Element buildXmlHierarchy() {
        if(xml==null){
            String dom = getPageSource();
            Document doc = Jsoup.parse(dom);
            xml = doc.getElementsByTag("body").get(0);
        }
        return xml;
    }

    private void compileXml(StringBuilder dom, WindowsElement element){

        List<WindowsElement> children = element.getAllChildrenElements();
        for(WindowsElement child : children){
            hierarchyId++;
            String tag = child.getTagName().replace(" ","-");;
            dom.append("<"+tag);
            appendAttributeToXml(dom, child, "name");
            appendAttributeToXml(dom, child, "id");
            appendAttributeToXml(dom, child, "classname");
            //appendAttributeToXml(front, child, "size");
            appendAttributeToXml(dom, child, "frameworkid");
            // appendAttributeToXml(front, child, "hwnd");
            appendAttributeToXml(dom, child, "text");
            dom.append(" hierarchyId=\""+hierarchyId+"\"");
            queryTable.put(hierarchyId,child);
            dom.append(">");
            if(children.size()>0){
                compileXml(dom, child);
            }
            dom.append("</"+tag+">");
        }
    }

    private void appendAttributeToXml(StringBuilder sb, WindowsElement element, String propertyName){
        String value = element.getAttribute(propertyName);
        if(!value.equals("")&&!value.equals("null")){
            sb.append(" "+propertyName+"=\""+value+"\"");
        }
    }

    public HashMap<Integer, WindowsElement> getQueryTable() {
        return queryTable;
    }

    private HashMap<Integer, WindowsElement> queryTable;
    private Integer hierarchyId;
    @Override
    public String getPageSource() {
        queryTable = new HashMap<>();
        StringBuilder dom = new StringBuilder();
        hierarchyId=0;
        WindowsElement mainWindow = new WindowsElement(windowElement,this);
        String tag = mainWindow.getTagName().replace(" ","-");
        if(tag.equals("")){
            tag = "undefined";
        }
        dom.append("<"+tag);
        appendAttributeToXml(dom, mainWindow, "name");
        appendAttributeToXml(dom, mainWindow, "id");
        appendAttributeToXml(dom, mainWindow, "classname");
        appendAttributeToXml(dom, mainWindow, "frameworkid");
        appendAttributeToXml(dom, mainWindow, "text");
        dom.append(" hierarchyId=\""+hierarchyId+"\"");
        queryTable.put(hierarchyId,mainWindow);
        dom.append(">");
        if(mainWindow.getAllChildrenElements().size()>0){
            compileXml(dom, mainWindow);
        }
        dom.append("</"+tag+">");



        return dom.toString();
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

    @Override
    public void perform(Collection<Sequence> actions) {
        throw new UnsupportedCommandException ("HTML API not implemented in EY Windows Driver");
    }

    @Override
    protected Response execute(String driverCommand, Map<String, ?> parameters) {
        command.execute(driverCommand, parameters);
        return new Response();
    }

    public class WindowsTargetLocator implements WebDriver.TargetLocator {
        @Override
        public WebDriver frame(int i) {
            return null;
        }

        @Override
        public WebDriver frame(String stringBy) {
            WindowsBy by = new WindowsBy(stringBy);
            PointerByReference frameAttributes =iuiAutomation.createPropertyCondition(by.getAttributeIndex(), by.getAttributeValue().toString());
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
            PointerByReference windowAttributes = iuiAutomation.createAndCondition(iuiAutomation.createPropertyCondition(by.getAttributeIndex(), by.getAttributeValue().toString()), iuiAutomation.createPropertyCondition(tagBy.getAttributeIndex(), tagBy.getAttributeValue().toString()));
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

    public WindowsElement findElementByRect(String rect){
        String[] parts = rect.split("\\.");
        List<WebElement> eles = findElements(By.tagName(parts[0]));
        for(WebElement ele : eles){
            org.openqa.selenium.Rectangle rectangle = ele.getRect();
            String rectActual = "[l="+rectangle.getX()+",t=" +rectangle.getY()+",r="+(rectangle.getX()+rectangle.getWidth())+ ",b=" + (rectangle.getY()+rectangle.getHeight())+"]";
            if(rectActual.equals(parts[1])){
                return (WindowsElement) ele;
            }
        }
        return null;
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
