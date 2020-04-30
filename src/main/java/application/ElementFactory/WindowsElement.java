package application.ElementFactory;

import Infrastructure.Automation.IUIAutomationElement;
import Infrastructure.Automation.Patterns.InvokePattern;
import Infrastructure.Automation.Patterns.ValuePattern;
import Infrastructure.Utils.LibraryBuilder;
import com.sun.jna.platform.win32.OaIdl;
import com.sun.jna.ptr.PointerByReference;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.Locatable;

import java.util.List;

public class WindowsElement implements WebElement, Locatable {

    public IUIAutomationElement getIUIAutomationElement() {
        return element;
    }

    private IUIAutomationElement element;
    private int invokePatternID = 10000;
    private int valuePatternID = 10002;
    private LibraryBuilder libraryBuilder;

    public WindowsElement(IUIAutomationElement element, LibraryBuilder libraryBuilder){
        this.element = element;
        this.libraryBuilder = libraryBuilder;
    }

    @Override
    public void click() {
        PointerByReference patternPointer = element.getCurrentPattern(invokePatternID);
        InvokePattern invokePattern = new InvokePattern(libraryBuilder.loadIuiAutomationInvokeLibrary(patternPointer));
        invokePattern.invoke();
    }

    @Override
    public void submit() {

    }

    @Override
    public void sendKeys(CharSequence... charSequences) {
        PointerByReference patternPointer = element.getCurrentPattern(valuePatternID);
        ValuePattern valuePattern = new ValuePattern(libraryBuilder.loadIuiAutomationValueLibrary(patternPointer));
        valuePattern.setValue(charSequences[0].toString());
    }

    @Override
    public void clear() {
        PointerByReference patternPointer = element.getCurrentPattern(valuePatternID);
        ValuePattern valuePattern = new ValuePattern(libraryBuilder.loadIuiAutomationValueLibrary(patternPointer));
        valuePattern.setValue("");
    }

    @Override
    public String getTagName() {
        Object var = element.getCurrentPropertyValue("tagname");
        return var.toString();
    }

    @Override
    public String getAttribute(String property) {
        Object var = element.getCurrentPropertyValue(property);
        return var.toString();
    }

    @Override
    public boolean isSelected() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        Object var = element.getCurrentPropertyValue("isEnabled");
        Boolean enabled = ((OaIdl.VARIANT_BOOL) var).booleanValue();
        return enabled;
    }

    @Override
    public String getText() {
        Object var = element.getCurrentPropertyValue("text");
        return var.toString();
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
