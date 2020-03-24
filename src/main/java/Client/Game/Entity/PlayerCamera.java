package Client.Game.Entity;

import Client.Events.MouseCursorEvent;
import org.joml.Vector3f;

public final class PlayerCamera {

    private final Vector3f rotation = new Vector3f(0, 0, 0);
    private final Vector3f position = new Vector3f(0, 0, 0);

    private double oldMouseX, oldMouseY;
    private final float mouseSpeed;

    public PlayerCamera(float mouseSpeed){
        this.mouseSpeed = mouseSpeed;
    }

    public void enableControl(){
        float dx = (float) (MouseCursorEvent.getMouseX() - oldMouseX);
        float dy = (float) (MouseCursorEvent.getMouseY() - oldMouseY);

        rotation.x += (-dy * mouseSpeed) / 16;
        rotation.y += (-dx * mouseSpeed) / 16;

        oldMouseX = MouseCursorEvent.getMouseX();
        oldMouseY = MouseCursorEvent.getMouseY();
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }
}
