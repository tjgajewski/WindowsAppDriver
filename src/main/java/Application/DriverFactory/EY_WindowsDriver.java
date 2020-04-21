package Application.DriverFactory;


import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class EY_WindowsDriver extends RemoteWebDriver implements WebDriver, SearchContext {


    private DriverBuilder driverBuilder;

    public EY_WindowsDriver(){


    }
    public EY_WindowsDriver(DesiredCapabilities capabilities){

    }
    private void initializeDriver(){
        driverBuilder = new DriverBuilder();
        driverBuilder.build();
    }



    @Override
    public void get(String s) {

    }

    @Override
    public String getCurrentUrl() {
        return null;
    }

    @Override
    public String getTitle() {
        return null;
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
    public String getPageSource() {
        return null;
    }

    @Override
    public void close() {

    }

    @Override
    public void quit() {

    }

    @Override
    public Set<String> getWindowHandles() {
        return null;
    }

    @Override
    public String getWindowHandle() {
        return null;
    }

    @Override
    public TargetLocator switchTo() {
        return null;
    }

    @Override
    public Navigation navigate() {
        return null;
    }

    @Override
    public Options manage() {
        return null;
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> outputType) throws WebDriverException {
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        java.awt.Rectangle screenRect = new Rectangle(new Point(0,0),screenSize);
        BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
        String base64EncodedPng = null;
        try {
            base64EncodedPng = encodeFileToBase64Binary(screenFullImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputType.convertFromBase64Png(base64EncodedPng);
    }

    private String encodeFileToBase64Binary(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();
        return new String(Base64.encodeBase64(imageInByte));
    }
}
