package application.driver.command;

import application.actions.factory.WindowsActions;
import application.actions.factory.WindowsActionsInterface;
import application.driver.factory.DriverBuilder;
import application.driver.factory.WindowsDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class WindowsDriverCommands {

    private HashMap<String, Method> methodHashMap;
    private WindowsActionsInterface action;

    /**
     * Instantiates a new Windows driver commands.
     *
     * @param driver      the driver
     */
    public WindowsDriverCommands(WindowsDriver driver){
        action = chooseActions(driver);
        methodHashMap = new HashMap<>();
        Method[] methods = action.getClass().getMethods();
        for (Method method : methods) {
            methodHashMap.put(method.getName(),method);
        }
    }

    /**
     * Execute.
     *
     * @param command           the command
     * @param parameters        the parameters
     */
    public void execute(String command, Map<String, ?> parameters){
        try {
            methodHashMap.get(command).invoke(action, parameters);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private WindowsActionsInterface chooseActions(WindowsDriver driver){
        return new WindowsActions(driver);
    }
}
