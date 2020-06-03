package application.element.factory;

import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.Coordinates;

public class WindowsElementCoordinates implements Coordinates {

    private WindowsElement element;

    public WindowsElementCoordinates(WindowsElement element){
        this.element = element;
    }

    @Override
    public Point onScreen() {
        return null;
    }

    @Override
    public Point inViewPort() {
        return null;
    }

    @Override
    public Point onPage() {
        return null;
    }

    @Override
    public Object getAuxiliary() {
        return element.dynamicElementId;
    }
}
