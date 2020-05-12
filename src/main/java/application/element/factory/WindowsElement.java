package application.element.factory;

import infrastructure.automationapi.IUIAutomationElement;
import infrastructure.automationapi.patterns.InvokePattern;
import infrastructure.automationapi.patterns.SelectItemPattern;
import infrastructure.automationapi.patterns.ValuePattern;
import infrastructure.windowsapi.Keyboard;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.List;

public class WindowsElement extends RemoteWebElement implements WebElement, Locatable {

    private IUIAutomationElement element;

    public WindowsElement(IUIAutomationElement element){
        this.element = element;
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

    @Override
    public void click() {
        if(InvokePattern.isAvailableForElement(element)) {
            new InvokePattern(element).invoke();
        }
        else if(SelectItemPattern.isAvailableForElement(element)){
            new SelectItemPattern(element).select();
        }
        else {
            throw new ElementNotSelectableException("Element has no supported click methods");
        }
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
        return null;
    }

    @Override
    public Dimension getSize() {
        return null;
    }

    @Override
    public Rectangle getRect() {
        return null;
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
        return null;
    }

}
