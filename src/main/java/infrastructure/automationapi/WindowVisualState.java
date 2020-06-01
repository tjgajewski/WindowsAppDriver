package infrastructure.automationapi;

import java.util.HashMap;
import java.util.Map;

public enum WindowVisualState {
    NORMAL(0),
    MAXIMIZED(1),
    MINIMIZED(2);

    private final int desiredVisualStateInt;

    public int getDesiredStateIntValue() {
        return this.desiredVisualStateInt;
    }

    WindowVisualState(int visualStateInt) {
        this.desiredVisualStateInt = visualStateInt;
    }

    private static final Map<Integer, WindowVisualState> INT_TO_TYPE_MAP = new HashMap<>();
    static {
        for (WindowVisualState type : WindowVisualState.values()) {
            INT_TO_TYPE_MAP.put(type.desiredVisualStateInt, type);
        }
    }

    public static WindowVisualState fromInt(final int i) {
        WindowVisualState type = INT_TO_TYPE_MAP.get(i);
        if (type == null) {
            return WindowVisualState.NORMAL;
        }
        return type;
    }
}
