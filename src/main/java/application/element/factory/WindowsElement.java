package application.element.factory;

import com.sun.jna.platform.win32.WinDef;
import infrastructure.automationapi.IUIAutomationElement;
import infrastructure.automationapi.patterns.InvokePattern;
import infrastructure.automationapi.patterns.SelectItemPattern;
import infrastructure.automationapi.patterns.ValuePattern;
import infrastructure.robotapi.Robo;
import infrastructure.windowsapi.Keyboard;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.List;

public class WindowsElement extends RemoteWebElement implements WebElement, Locatable {

    private IUIAutomationElement element;
    protected String dynamicElementId;

    public WindowsElement(IUIAutomationElement element, String dynamicElementId){
        this.element = element;
        this.dynamicElementId = dynamicElementId;
    }

    public IUIAutomationElement getIUIAutomationElement() {
        return element;
    }

    public String toString(){
        return element.getCurrentPropertyValue("name").stringValue();
    }

    public void setFocus(){
        element.setFocus();
    }

    public void moveToElement(){
        Point clickablePoint = getLocation();
        Robo.getRoboInstance().mouseMove(clickablePoint);
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
        return element.getCurrentPropertyValue("tagname").stringValue();
    }

    @Override
    public String getAttribute(String property) {
        return element.getCurrentPropertyValue(property).stringValue();
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
        return true;
    }

    @Override
    public Point getLocation() {
        WinDef.POINT point = element.getClickablePoint();
        return new Point(point.x,point.y);
    }

    @Override
    public Dimension getSize() {
        return null;
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

}
