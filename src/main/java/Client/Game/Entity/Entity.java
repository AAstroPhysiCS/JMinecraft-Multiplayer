package Client.Game.Entity;

import Client.Engine.Graphics.Texture;
import Client.Game.World.Chunk.Chunk;
import Client.Game.World.IWorldConstants;
import org.joml.Vector3f;

public abstract class Entity {

    protected PlayerCamera camera;
    protected Vector3f viewDirection = new Vector3f();

    protected float horizontalSpeed = 0.15f;
    protected float verticalSpeed = 0.15f;

    protected static Texture texture;
    protected float[] vertices, tcs;
    protected int[] indices;

    protected boolean floorState;

    protected static final float defaultMouseSpeed = 0.15f;
    public static final float ENTITY_HEIGHT = 1.5f, ENTITY_WIDTH = 0.5f, ENTITY_DEPTH = 0.2f;

    public Entity(float mouseSpeed) {
        camera = new PlayerCamera(mouseSpeed);
    }

    public Entity() {
        camera = new PlayerCamera(defaultMouseSpeed);
    }

    public static void loadTexture() {
        texture = new Texture("images/steve.png", true);
    }

    public void updateVertices() {
        vertices = new float[]{
                //front
                -ENTITY_WIDTH + camera.getPosition().x, -ENTITY_HEIGHT + camera.getPosition().y, ENTITY_DEPTH + camera.getPosition().z,
                ENTITY_WIDTH + camera.getPosition().x, -ENTITY_HEIGHT + camera.getPosition().y, ENTITY_DEPTH + camera.getPosition().z,
                -ENTITY_WIDTH + camera.getPosition().x, ENTITY_HEIGHT + camera.getPosition().y, ENTITY_DEPTH + camera.getPosition().z,
                ENTITY_WIDTH + camera.getPosition().x, ENTITY_HEIGHT + camera.getPosition().y, ENTITY_DEPTH + camera.getPosition().z,
        };
    }

    public void updateIndices() {
        indices = new int[]{
                0, 1, 3,
                3, 0, 2,

                4, 5, 7,
                7, 4, 6,

                8, 9, 11,
                11, 8, 10,

                12, 13, 15,
                15, 12, 14,

                16, 17, 19,
                19, 16, 18,

                20, 21, 23,
                23, 20, 22,
        };
    }

    public void setTcs() {
        tcs = new float[]{
                0.312f, 0.199f,
                0.0f, 0.199f,
                0.312f, 0.813f,
                0.0f, 0.813f,
        };
    }

    public boolean closeToChunk(Chunk chunk) {
        return chunk.calcDistanceToEntity(this) < IWorldConstants.VIEW_DISTANCE;
    }

    public abstract void update();

    public PlayerCamera getCamera() {
        return camera;
    }

    public Vector3f getViewDirection() {
        return viewDirection;
    }

    public static Texture getTexture() {
        return texture;
    }

    public void setHorizontalSpeed(float horizontalSpeed) {
        this.horizontalSpeed = horizontalSpeed;
    }

    public void setVerticalSpeed(float verticalSpeed) {
        this.verticalSpeed = verticalSpeed;
    }

    public float getHorizontalSpeed() {
        return horizontalSpeed;
    }

    public float getVerticalSpeed() {
        return verticalSpeed;
    }
}
