package Client.Events;

import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class KeyboardEvent extends GLFWKeyCallback {

    private static final boolean[] keys = new boolean[500];

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        keys[key] = action != GLFW_RELEASE;
    }

    public static boolean[] getKeys() {
        return keys;
    }
}
