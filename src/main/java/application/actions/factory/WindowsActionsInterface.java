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
     */
    void mouseMoveTo(Map<String, ?> parameters);

    /**
     * Mouse click.
     *
     * @param parameters        the parameters
     */
    void mouseClick(Map<String, ?> parameters);

    /**
     * Send keys to active element.
     *
     * @param parameters        the parameters
     */
    void sendKeysToActiveElement(Map<String, ?> parameters);

//    /**
//     * Mouse double click.
//     *
//     * @param parameters        the parameters
//     * @param generatedElements the generated elements
//     */
//    void doubleClick(Map<String, ?> parameters, HashMap<String, By> generatedElements);
}


