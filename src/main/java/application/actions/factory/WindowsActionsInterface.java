package application.actions.factory;

import application.element.factory.WindowsBy;
import org.openqa.selenium.By;

import java.util.HashMap;
import java.util.Map;

public interface WindowsActionsInterface {

    /**
     * Mouse move to.
     *
     * @param parameters        the parameters
     * @param generatedElements the generated elements
     */
    void mouseMoveTo(Map<String, ?> parameters, HashMap<String, By> generatedElements);

    /**
     * Mouse click.
     *
     * @param parameters        the parameters
     * @param generatedElements the generated elements
     */
    void mouseClick(Map<String, ?> parameters, HashMap<String, By> generatedElements);

    /**
     * Send keys to active element.
     *
     * @param parameters        the parameters
     * @param generatedElements the generated elements
     */
    void sendKeysToActiveElement(Map<String, ?> parameters, HashMap<String, By> generatedElements);
}
