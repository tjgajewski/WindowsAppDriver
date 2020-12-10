package application.element.factory;

import application.driver.DriverHelpers;
import application.driver.factory.WindowsDriver;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.COM.IDispatch;
import com.sun.jna.platform.win32.OaIdl;
import com.sun.jna.platform.win32.Variant;
import com.sun.jna.platform.win32.WTypes;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import infrastructure.automationapi.IUIAutomation;
import infrastructure.automationapi.IUIAutomationElement;
import infrastructure.automationapi.IUIAutomationElementArray;
import infrastructure.automationapi.patterns.InvokePattern;
import infrastructure.automationapi.patterns.SelectItemPattern;
import infrastructure.automationapi.patterns.ValuePattern;
import infrastructure.robotapi.Robo;
import infrastructure.windowsapi.Keyboard;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.util.ArrayList;
import java.util.List;

public class WindowsElement implements WebElement, Locatable {

    private IUIAutomationElement element;
    protected String dynamicElementId="-1";
    public WindowsDriver getWrappedDriver() {
        return driver;
    }
    public void setWrappedDriver(WindowsDriver driver) {
        this.driver = driver;
    }
    WindowsDriver driver;



    public WindowsElement(IUIAutomationElement element, String dynamicElementId, WindowsDriver driver){
        this.driver = driver;
        this.element = element;
        this.dynamicElementId = dynamicElementId;
    }

    public int[] getRuntimeId(){
        PointerByReference ar = element.getRuntimeId();
        int[] array = new int[4];
        ar.getValue().read(0,array,0,4);

            return array;
    }

    public WindowsElement(By by, String dynamicElementId, IUIAutomation iuiAutomation, IUIAutomationElement frameElement, WindowsDriver driver){

        this.dynamicElementId = dynamicElementId;
        this.driver = driver;
        IUIAutomationElement element = ElementHelpers.getIUIAutomationElement(by, iuiAutomation, frameElement, dynamicElementId, driver);
        this.element = element;
    }

    public WindowsElement(WindowsBy by, String dynamicElementId, IUIAutomation iuiAutomation, IUIAutomationElement frameElement, WindowsDriver driver){

        this.dynamicElementId = dynamicElementId;
        this.driver = driver;
        IUIAutomationElement element = ElementHelpers.getIUIAutomationElement(by, iuiAutomation, frameElement, dynamicElementId, driver);
        this.element = element;
    }

    public IUIAutomationElement getIUIAutomationElement() {
        return element;
    }

    public String toString(){
        return getAttribute("name");
    }

    public void setFocus(){
        element.setFocus();
    }

    public void moveToElement(){
        Point clickablePoint = getLocation();
        Robo.getRoboInstance().mouseMove(clickablePoint);
    }

    public WindowsElement getParent(){
        return new WindowsElement(driver.getIuiAutomation().getTreeWalker().getParentElement(element), "-1", driver);
    }
    public WindowsElement getPreviousSibling(){
        return new WindowsElement(driver.getIuiAutomation().getTreeWalker().getPreviousSiblingElement(element), "-1", driver);
    }
    public WindowsElement getNextSibling(){
        return new WindowsElement(driver.getIuiAutomation().getTreeWalker().getNextSiblingElement(element), "-1", driver);
    }

    @Override
    public void click() {
        if(InvokePattern.isAvailableForElement(element)) {

            new InvokePattern(element).invoke();
        }
        else if(SelectItemPattern.isAvailableForElement(element)){
            new SelectItemPattern(element).select();
        }
        else {
            Point clickablePoint = getLocation();
            Robo.getRoboInstance().mouseMove(clickablePoint);
            Robo.getRoboInstance().leftClick();
            Robo.getRoboInstance().returnMouseAfterMove();
        }
    }

    public void rightClick(){
        Point clickablePoint = getLocation();
        Robo.getRoboInstance().mouseMove(clickablePoint);
        Robo.getRoboInstance().rightClick();
        Robo.getRoboInstance().returnMouseAfterMove();
    }

    @Override
    public void submit() {

    }

    @Override
    public void sendKeys(CharSequence... charSequences) {
    for (int i = 0; i < charSequences.length; i++) {
        if (ValuePattern.isAvailableForElement(element) && charSequences[i] instanceof String && charSequences[i].charAt(0)!=Keyboard.CONTROL_KEY && charSequences[i].charAt(0)!=Keyboard.SHIFT_KEY && charSequences[i].charAt(0)!=Keyboard.ALT_KEY) {
            new ValuePattern(element).setValue(charSequences[i].toString());
        } else {
            setFocus();
            Keyboard.sendInput(charSequences[i]);
        }
    }
    }


    @Override
    public void clear() {
        new ValuePattern(element).setValue("");
    }

    @Override
    public String getTagName() {
        String tag = element.getCurrentPropertyValue("tagname").stringValue();
        if(tag.equals("")){
            tag = "undefined";
        }
        return tag;
    }

    @Override
    public String getAttribute(String property) {
        Object var= element.getCurrentPropertyValue(property).getValue();
        if(var == null){
            return "";
        }
        return var.toString();
    }

    public Object getProperty(String property) {
        Variant.VARIANT.ByReference propertyVal = element.getCurrentPropertyValue(property);
        return propertyVal.getValue();
    }

    public WinDef.HWND getNativeWindowHandle() {
        return element.getNativeWindowhandle();
       // return new WinDef.HWND(Pointer.createConstant(Integer.valueOf(value.toString())));
    }

    @Override
    public boolean isSelected() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return element.getCurrentPropertyValue("isenabled").booleanValue();
    }

    @Override
    public String getText() {
        return element.getCurrentPropertyValue("text").stringValue();
    }

    @Override
    public List<WebElement> findElements(By by) {
        return null;
    }

    @Override
    public WebElement findElement(By by) {

        return null;
    }

    @Override
        public boolean isDisplayed() {
        if(element.getCurrentBoundingRectangle().toRectangle().width > 0 || element.getCurrentBoundingRectangle().toRectangle().height > 0){ return true; }
        else{ return false; }
    }

    @Override
    public Point getLocation() {
        WinDef.POINT point = element.getClickablePoint();
        return new Point(point.x,point.y);
    }

    @Override
    public Dimension getSize() {
        WinDef.RECT rect2 = element.getCurrentBoundingRectangle();
        int width = rect2.toRectangle().width;
        int height= rect2.toRectangle().height;
        return new Dimension(width,height);
    }

    @Override
    public Rectangle getRect() {
        WinDef.RECT rect2 = element.getCurrentBoundingRectangle();
        int x = (int) rect2.toRectangle().getX();
        int y = (int) rect2.toRectangle().getY();
        int width = rect2.toRectangle().width;
        int height= rect2.toRectangle().height;
        return new Rectangle(x,y,height,width);
    }

    public WinDef.RECT getWinDefRect() {
       return element.getCurrentBoundingRectangle();
    }

    @Override
    public String getCssValue(String s) {
        return null;
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> outputType) throws WebDriverException {
        return null;
    }

    @Override
    public Coordinates getCoordinates() {
        return new WindowsElementCoordinates(this);
    }


public List<WindowsElement> getAllDescendentElements(){
        PointerByReference trueCondPtr = new PointerByReference();
    driver.getIuiAutomation().createTrueCondition(trueCondPtr);
    IUIAutomationElementArray elements = element.findAll(trueCondPtr, new WindowsBy(By.id("ALL ElEMENTS")));
    List<WindowsElement> elementsList = new ArrayList<>();
    for(int i = 0; i < elements.getLength(); i++){
        String dynamicElementId = String.valueOf(driver.generatedElements.size());
        driver.generatedElements.put(dynamicElementId,null);
        WindowsElement currentElement = new WindowsElement(elements.getElement(i),dynamicElementId,driver);
        elementsList.add(currentElement);
    }
    return elementsList;
}

    public List<WindowsElement> getAllChildrenElements(){
        PointerByReference trueCondPtr = new PointerByReference();
        driver.getIuiAutomation().createTrueCondition(trueCondPtr);
        IUIAutomationElementArray elements = element.findAllFirstDescendent(trueCondPtr, new WindowsBy(By.id("ALL ElEMENTS")));
        List<WindowsElement> elementsList = new ArrayList<>();
        for(int i = 0; i < elements.getLength(); i++){
            String dynamicElementId = String.valueOf(driver.generatedElements.size());
            driver.generatedElements.put(dynamicElementId,null);
            WindowsElement currentElement = new WindowsElement(elements.getElement(i),dynamicElementId,driver);
            elementsList.add(currentElement);
        }
        return elementsList;
    }

    public List<WindowsElement> getAllChildrenElements(By by){
        WindowsBy windowsBy = new WindowsBy(by);
        PointerByReference propertyCondition = driver.getIuiAutomation().createPropertyCondition(windowsBy.getAttributeIndex(), windowsBy.getAttributeValue());
        IUIAutomationElementArray elements = element.findAllFirstDescendent(propertyCondition, windowsBy);
        List<WindowsElement> elementsList = new ArrayList<>();
        for(int i = 0; i < elements.getLength(); i++){
            String dynamicElementId = String.valueOf(driver.generatedElements.size());
            driver.generatedElements.put(dynamicElementId,null);
            WindowsElement currentElement = new WindowsElement(elements.getElement(i),dynamicElementId,driver);
            elementsList.add(currentElement);
        }
        return elementsList;
    }

    public List<WindowsElement> getAllAncestorElements(){
        List<WindowsElement> elementsList = new ArrayList<>();
        WindowsElement element = this;
        while(true){

            element = element.getParent();
            if(element.getIUIAutomationElement().getPointerByRefToElement().getValue()!=null){
                elementsList.add(element);
            }
            else{
                break;
            }
        }
        return elementsList;
    }

    public String getAbsXpath(){
        WindowsElement element = this;
        StringBuilder absXpath = new StringBuilder();
        String tagName0 = element.getTagName().replace(" ","-");
        int index0 =1;
        WindowsElement sib0 = element;
        while(true){
            sib0 = sib0.getPreviousSibling();
            if(sib0.getIUIAutomationElement().getPointerByRefToElement().getValue()==null){
                break;
            }
            if(tagName0.equals(sib0.getTagName().replace(" ","-"))){
                index0++;
            }
        }
        if(index0>1){
            absXpath.insert(0,element.getTagName().replace(" ","-")+"["+index0+"]");
        }
        else {
            absXpath.insert(0, element.getTagName().replace(" ","-"));
        }
        String hwnd = driver.getWindowElement().getCurrentPropertyValue("hwnd").getValue().toString();
        while(true){

            element = element.getParent();

            if(element.getIUIAutomationElement().getPointerByRefToElement().getValue()!=null){
                if(hwnd.equals(element.getIUIAutomationElement().getCurrentPropertyValue("hwnd").getValue().toString())){
                    absXpath.insert(0, element.getTagName().replace(" ","-") + "/");
                    break;
                }
                String tagName = element.getTagName().replace(" ","-");
                int index =1;
                WindowsElement sib = element;
                while(true){
                    sib = sib.getPreviousSibling();
                    if(sib.getIUIAutomationElement().getPointerByRefToElement().getValue()==null){
                        break;
                    }
                    if(tagName.equals(sib.getTagName().replace(" ","-"))){
                        index++;
                    }
                }
                if(index>1){
                    absXpath.insert(0,element.getTagName().replace(" ","-")+"["+index+"]/");
                }
                else {
                    absXpath.insert(0, element.getTagName().replace(" ","-") + "/");
                }
            }
            else{
                break;
            }


        }
        return absXpath.toString();
    }

    public List<WindowsElement> getAllSiblings(){
        return getParent().getAllChildrenElements();
    }

    public List<WindowsElement> getAllSiblings(By by){
        return getParent().getAllChildrenElements(by);
    }

    public boolean equals(Object object){
        if(object == null){
            return false;
        }
        else if(!(object instanceof WindowsElement)){
            return false;
        }
        else{
            WindowsElement element = (WindowsElement) object;
            if(getAbsXpath().equals(element.getAbsXpath())&&getAttribute("name").equals(element.getAttribute("name"))&&getAttribute("id").equals(element.getAttribute("id"))){
                return true;
            }
        }

        return false;
    }
}
