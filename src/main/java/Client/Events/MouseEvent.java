package Client.Events;

import org.lwjgl.glfw.GLFWMouseButtonCallback;

import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseEvent extends GLFWMouseButtonCallback {

    private static final boolean[] buttons = new boolean[128];

    @Override
    public void invoke(long window, int button, int action, int mods) {
        buttons[button] = action != GLFW_RELEASE;
    }

    public static boolean[] getButtons() {
        return buttons;
    }
}
