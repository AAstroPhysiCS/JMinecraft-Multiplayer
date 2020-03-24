package Client.Game.World.Block;

import Client.Game.Entity.Entity;
import org.joml.Vector3f;

public enum BlockFace {
    FRONT(0.55f), BACK(0.4f), LEFT(0.65f), RIGHT(0.65f), TOP(0.8f), BOTTOM(0.4f);

    private float[] vertices;
    private boolean selected;
    private Vector3f pos;
    private double distance;
    private final float brightness;

    BlockFace(float brightness) {
        this.brightness = brightness;
    }

    public void setVertices(float[] vertices, int startIndex, int endIndex) {
        this.vertices = new float[12];
        int pointer = 0;
        for (int i = startIndex; i < endIndex; i++) {
            this.vertices[pointer++] = vertices[i];
        }
    }

    public void setDistanceToEntity(Entity entity) {
        distance = Math.sqrt(Math.pow(pos.x - entity.getCamera().getPosition().x, 2)
                + Math.pow(pos.y - entity.getCamera().getPosition().y, 2)
                + Math.pow(pos.z - entity.getCamera().getPosition().z, 2));
    }

    public double getDistance() {
        return distance;
    }

    public Vector3f getPos() {
        return pos;
    }

    public void setPos(Vector3f pos) {
        this.pos = pos;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public float getBrightness() {
        return brightness;
    }

    public boolean isSelected() {
        return selected;
    }

    public float[] getVertices() {
        return vertices;
    }
}
