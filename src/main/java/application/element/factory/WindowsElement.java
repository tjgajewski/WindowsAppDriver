package application.element.factory;

import infrastructure.automation.IUIAutomationElement;
import infrastructure.automation.patterns.InvokePattern;
import infrastructure.automation.patterns.SelectItemPattern;
import infrastructure.automation.patterns.ValuePattern;
import infrastructure.utils.LibraryBuilder;
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
    private int isInvokePatternAvailID = 30031;
    private int valuePatternID = 10002;
    private int isValuePatternAvailID = 30043;

    private int selectItemPatternID = 10010;
    private int isSelectItemPatternAvailID = 30036;

    private LibraryBuilder libraryBuilder;

    public WindowsElement(IUIAutomationElement element, LibraryBuilder libraryBuilder){
        this.element = element;
        this.libraryBuilder = libraryBuilder;
    }

    @Override
    public void click() {
        if(patternIsSupported(isInvokePatternAvailID)) {
            PointerByReference patternPointer = element.getCurrentPattern(invokePatternID);
            InvokePattern invokePattern = new InvokePattern(libraryBuilder.loadIuiAutomationInvokeLibrary(patternPointer));
            invokePattern.invoke();
        }
        else if(patternIsSupported(isSelectItemPatternAvailID)){
            PointerByReference patternPointer = element.getCurrentPattern(selectItemPatternID);
            SelectItemPattern selectItemPattern = new SelectItemPattern(libraryBuilder.loadIuIAutomationSelectItemLibrary(patternPointer));
            selectItemPattern.select();
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

    private Boolean patternIsSupported(int patternID){
        Object var = element.getCurrentPropertyValue(patternID);
        return ((OaIdl.VARIANT_BOOL) var).booleanValue();
    }
}
