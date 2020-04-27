package Application.DriverFactory;

import Infrastructure.Automation.IUIAutomation;
import Infrastructure.Automation.IUIAutomationElement;
import Infrastructure.Utils.LibraryBuilder;
import com.sun.jna.ptr.PointerByReference;
import org.apache.commons.io.FilenameUtils;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class DriverBuilder {

    EY_WindowsDriver driver;

    public DriverBuilder(EY_WindowsDriver driver){
        this.driver = driver;
    }

    private final String APPLICATION_FILE_PATH = "applicationPath";
    private final String LOAD_TIME_OUT = "loadTimeOut";
    private final String POLL_RATE = "pollRate";
    private final String OPEN_APPLICATION = "openApplication";
    private final String APPLICATION_NAME = "applicationName";

    public void configureCapabilities(DesiredCapabilities capabilities) {
        if(capabilities.getCapabilityNames().contains(APPLICATION_FILE_PATH)) {
            if (!capabilities.getCapabilityNames().contains(OPEN_APPLICATION)) {
                capabilities.setCapability(OPEN_APPLICATION, "auto");
            }
            if (!capabilities.getCapabilityNames().contains(LOAD_TIME_OUT)) {
                capabilities.setCapability(LOAD_TIME_OUT, 30);
            }
            if (!capabilities.getCapabilityNames().contains(POLL_RATE)) {
                capabilities.setCapability(POLL_RATE, 1);
            }
            if (!capabilities.getCapabilityNames().contains(APPLICATION_NAME)) {
                capabilities.setCapability(APPLICATION_NAME, new File(capabilities.getCapability(APPLICATION_FILE_PATH).toString()).getName());
            }
        }
        driver.capabilities = capabilities;
    }

    public void connectToInterface(){
        driver.libraryBuilder = new LibraryBuilder();
        driver.iuiAutomation = new IUIAutomation(driver.libraryBuilder.loadIuiAutomationLibrary());
        PointerByReference rootElementPointer = driver.iuiAutomation.getRootElement();
        driver.iuiAutomationElementLib = driver.libraryBuilder.loadIuiAutomationElementLibrary(rootElementPointer);
        driver.rootElement = new IUIAutomationElement(driver.iuiAutomationElementLib);
        driver.windowElement = driver.rootElement;
    }

    public void build() {
        if(driver.capabilities.getCapabilityNames().contains(APPLICATION_FILE_PATH)) {
            openApplication(driver.capabilities.getCapability(APPLICATION_FILE_PATH).toString(), driver.capabilities.getCapability(OPEN_APPLICATION).toString());
        }
    }

    private void openApplication(String applicationPath, String determine){
        String appName = driver.capabilities.getCapability(APPLICATION_NAME).toString();
        if(determine.equalsIgnoreCase("auto")){
            String openProcesses = null;
            try {
                openProcesses = openProcesses();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(!openProcesses.contains(appName)){
                determine = "yes";
            }
        }
        if(determine.equalsIgnoreCase("yes")){
            try {
                Runtime.getRuntime().exec(applicationPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                waitForAppToOpen(appName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private String openProcesses() throws IOException {
        String line;
        Process p =Runtime.getRuntime().exec("tasklist");
        BufferedReader input =  new BufferedReader(new InputStreamReader(p.getInputStream()));
        StringBuilder sb = new StringBuilder();
        while ((line = input.readLine()) != null) {
            sb.append(line);
            sb.append("\n");
        }
        input.close();
        return sb.toString();
    }

    private void waitForAppToOpen(String app) throws IOException {
        long startMiliseconds = System.currentTimeMillis();
        long maxWait = Long.parseLong(driver.capabilities.getCapability(LOAD_TIME_OUT).toString())*1000;
        Boolean waitForInit = false;
        do {
            waitForInit = openProcesses().contains(app);
            long currentMiliseconds = System.currentTimeMillis();
            if(startMiliseconds+maxWait<currentMiliseconds){
                throw new WebDriverException("Failed waiting for application " + app + " to launch, tried for " + driver.capabilities.getCapability(LOAD_TIME_OUT).toString() + " seconds");
            }
            try {
                Thread.sleep(Long.parseLong(driver.capabilities.getCapability(POLL_RATE).toString())*1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }while(!waitForInit);
    }

    public void closeApplication() {
        String appName;
        if(driver.capabilities.getCapabilityNames().contains(APPLICATION_NAME)){
            appName = driver.capabilities.getCapability(APPLICATION_NAME).toString();

        try {
            Runtime.getRuntime().exec("taskkill /F /IM "+appName+" /T");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            waitForApplicationToClose(appName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        }
    }

    private void waitForApplicationToClose(String appName) throws IOException {
        long startMiliseconds = System.currentTimeMillis();
        long maxWait = Long.parseLong(driver.capabilities.getCapability(LOAD_TIME_OUT).toString());
        Boolean waitForClose = true;
        do {
            if(openProcesses().contains(appName)){
                waitForClose = false;
            }
            else{
                long currentMiliseconds = System.currentTimeMillis();
                if(startMiliseconds+maxWait<currentMiliseconds){
                    waitForClose = false;
                }
                try {
                    Thread.sleep(Long.parseLong(driver.capabilities.getCapability(POLL_RATE).toString()));
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }while(waitForClose);
    }

}