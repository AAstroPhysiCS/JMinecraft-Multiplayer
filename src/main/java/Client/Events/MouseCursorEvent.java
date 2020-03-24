package Client.Events;

import org.lwjgl.glfw.GLFWCursorPosCallback;

public final class MouseCursorEvent extends GLFWCursorPosCallback {

    private static double mouseX, mouseY;

    @Override
    public void invoke(long window, double xpos, double ypos) {
        mouseX = xpos;
        mouseY = ypos;
    }

    public static double getMouseX() {
        return mouseX;
    }

    public static double getMouseY() {
        return mouseY;
    }
}
