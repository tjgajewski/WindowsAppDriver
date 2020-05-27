package application.actions.factory;

import application.driver.factory.WindowsDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class WindowsActions extends Actions {


    public WindowsActions(WindowsDriver driver) {
        super(driver);
    }
}
