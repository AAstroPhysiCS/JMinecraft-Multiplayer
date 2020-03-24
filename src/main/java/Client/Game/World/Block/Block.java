package Client.Game.World.Block;

import Client.Engine.Graphics.Texture;
import Client.Game.Entity.Entity;
import org.joml.Vector3f;

import java.io.Serializable;

public abstract class Block implements Serializable {

    private static Texture texture;
    private boolean showing, selected;

    protected Vector3f position = new Vector3f(0, 0, 0);
    //verticesfull is with brightness, verticesPos just vertex points
    protected float[] verticesFull, verticesPos, tcs;
    protected int[] indices;

    public static final float BLOCK_WIDTH = 0.5f;
    public static final float BLOCK_HEIGHT = 0.5f;
    public static final float BLOCK_DEPTH = 0.5f;

    public Block() {
        setTcs();
    }

    public static void loadTexture() {
        texture = new Texture("images/world.png", true);
    }

    protected abstract void setTcs();

    public void createVertices() {
        verticesFull = new float[]{
                //front
                -BLOCK_WIDTH + position.x, -BLOCK_HEIGHT + position.y, BLOCK_DEPTH + position.z, BlockFace.FRONT.getBrightness(),
                BLOCK_WIDTH + position.x, -BLOCK_HEIGHT + position.y, BLOCK_DEPTH + position.z, BlockFace.FRONT.getBrightness(),
                -BLOCK_WIDTH + position.x, BLOCK_HEIGHT + position.y, BLOCK_DEPTH + position.z, BlockFace.FRONT.getBrightness(),
                BLOCK_WIDTH + position.x, BLOCK_HEIGHT + position.y, BLOCK_DEPTH + position.z, BlockFace.FRONT.getBrightness(),

                //back
                -BLOCK_WIDTH + position.x, -BLOCK_HEIGHT + position.y, -BLOCK_DEPTH + position.z, BlockFace.BACK.getBrightness(),
                BLOCK_WIDTH + position.x, -BLOCK_HEIGHT + position.y, -BLOCK_DEPTH + position.z, BlockFace.BACK.getBrightness(),
                -BLOCK_WIDTH + position.x, BLOCK_HEIGHT + position.y, -BLOCK_DEPTH + position.z, BlockFace.BACK.getBrightness(),
                BLOCK_WIDTH + position.x, BLOCK_HEIGHT + position.y, -BLOCK_DEPTH + position.z, BlockFace.BACK.getBrightness(),

                //left
                BLOCK_WIDTH + position.x, -BLOCK_HEIGHT + position.y, -BLOCK_DEPTH + position.z, BlockFace.LEFT.getBrightness(),
                BLOCK_WIDTH + position.x, -BLOCK_HEIGHT + position.y, BLOCK_DEPTH + position.z, BlockFace.LEFT.getBrightness(),
                BLOCK_WIDTH + position.x, BLOCK_HEIGHT + position.y, -BLOCK_DEPTH + position.z, BlockFace.LEFT.getBrightness(),
                BLOCK_WIDTH + position.x, BLOCK_HEIGHT + position.y, BLOCK_DEPTH + position.z, BlockFace.LEFT.getBrightness(),

                //right
                -BLOCK_WIDTH + position.x, -BLOCK_HEIGHT + position.y, -BLOCK_DEPTH + position.z, BlockFace.RIGHT.getBrightness(),
                -BLOCK_WIDTH + position.x, -BLOCK_HEIGHT + position.y, BLOCK_DEPTH + position.z, BlockFace.RIGHT.getBrightness(),
                -BLOCK_WIDTH + position.x, BLOCK_HEIGHT + position.y, -BLOCK_DEPTH + position.z, BlockFace.RIGHT.getBrightness(),
                -BLOCK_WIDTH + position.x, BLOCK_HEIGHT + position.y, BLOCK_DEPTH + position.z, BlockFace.RIGHT.getBrightness(),

                //top
                -BLOCK_WIDTH + position.x, -BLOCK_HEIGHT + position.y, BLOCK_DEPTH + position.z, BlockFace.TOP.getBrightness(),
                BLOCK_WIDTH + position.x, -BLOCK_HEIGHT + position.y, BLOCK_DEPTH + position.z, BlockFace.TOP.getBrightness(),
                -BLOCK_WIDTH + position.x, -BLOCK_HEIGHT + position.y, -BLOCK_DEPTH + position.z, BlockFace.TOP.getBrightness(),
                BLOCK_WIDTH + position.x, -BLOCK_HEIGHT + position.y, -BLOCK_DEPTH + position.z, BlockFace.TOP.getBrightness(),

                //bottom
                -BLOCK_WIDTH + position.x, BLOCK_HEIGHT + position.y, BLOCK_DEPTH + position.z, BlockFace.BOTTOM.getBrightness(),
                BLOCK_WIDTH + position.x, BLOCK_HEIGHT + position.y, BLOCK_DEPTH + position.z, BlockFace.BOTTOM.getBrightness(),
                -BLOCK_WIDTH + position.x, BLOCK_HEIGHT + position.y, -BLOCK_DEPTH + position.z, BlockFace.BOTTOM.getBrightness(),
                BLOCK_WIDTH + position.x, BLOCK_HEIGHT + position.y, -BLOCK_DEPTH + position.z, BlockFace.BOTTOM.getBrightness(),
        };
        verticesPos = new float[]{
                //front
                -BLOCK_WIDTH + position.x, -BLOCK_HEIGHT + position.y, BLOCK_DEPTH + position.z,
                BLOCK_WIDTH + position.x, -BLOCK_HEIGHT + position.y, BLOCK_DEPTH + position.z,
                -BLOCK_WIDTH + position.x, BLOCK_HEIGHT + position.y, BLOCK_DEPTH + position.z,
                BLOCK_WIDTH + position.x, BLOCK_HEIGHT + position.y, BLOCK_DEPTH + position.z,

                //back
                -BLOCK_WIDTH + position.x, -BLOCK_HEIGHT + position.y, -BLOCK_DEPTH + position.z,
                BLOCK_WIDTH + position.x, -BLOCK_HEIGHT + position.y, -BLOCK_DEPTH + position.z,
                -BLOCK_WIDTH + position.x, BLOCK_HEIGHT + position.y, -BLOCK_DEPTH + position.z,
                BLOCK_WIDTH + position.x, BLOCK_HEIGHT + position.y, -BLOCK_DEPTH + position.z,

                //left
                BLOCK_WIDTH + position.x, -BLOCK_HEIGHT + position.y, -BLOCK_DEPTH + position.z,
                BLOCK_WIDTH + position.x, -BLOCK_HEIGHT + position.y, BLOCK_DEPTH + position.z,
                BLOCK_WIDTH + position.x, BLOCK_HEIGHT + position.y, -BLOCK_DEPTH + position.z,
                BLOCK_WIDTH + position.x, BLOCK_HEIGHT + position.y, BLOCK_DEPTH + position.z,

                //right
                -BLOCK_WIDTH + position.x, -BLOCK_HEIGHT + position.y, -BLOCK_DEPTH + position.z,
                -BLOCK_WIDTH + position.x, -BLOCK_HEIGHT + position.y, BLOCK_DEPTH + position.z,
                -BLOCK_WIDTH + position.x, BLOCK_HEIGHT + position.y, -BLOCK_DEPTH + position.z,
                -BLOCK_WIDTH + position.x, BLOCK_HEIGHT + position.y, BLOCK_DEPTH + position.z,

                //top
                -BLOCK_WIDTH + position.x, -BLOCK_HEIGHT + position.y, BLOCK_DEPTH + position.z,
                BLOCK_WIDTH + position.x, -BLOCK_HEIGHT + position.y, BLOCK_DEPTH + position.z,
                -BLOCK_WIDTH + position.x, -BLOCK_HEIGHT + position.y, -BLOCK_DEPTH + position.z,
                BLOCK_WIDTH + position.x, -BLOCK_HEIGHT + position.y, -BLOCK_DEPTH + position.z,

                //bottom
                -BLOCK_WIDTH + position.x, BLOCK_HEIGHT + position.y, BLOCK_DEPTH + position.z,
                BLOCK_WIDTH + position.x, BLOCK_HEIGHT + position.y, BLOCK_DEPTH + position.z,
                -BLOCK_WIDTH + position.x, BLOCK_HEIGHT + position.y, -BLOCK_DEPTH + position.z,
                BLOCK_WIDTH + position.x, BLOCK_HEIGHT + position.y, -BLOCK_DEPTH + position.z,
        };
    }

    public void createIndices(int indicesX, int indicesY, int indicesZ) {
        indices = new int[]{
                indicesX, 1 + indicesY, 3 + indicesZ,
                3 + indicesX, indicesY, 2 + indicesZ,

                4 + indicesX, 5 + indicesY, 7 + indicesZ,
                7 + indicesX, 4 + indicesY, 6 + indicesZ,

                8 + indicesX, 9 + indicesY, 11 + indicesZ,
                11 + indicesX, 8 + indicesY, 10 + indicesZ,

                12 + indicesX, 13 + indicesY, 15 + indicesZ,
                15 + indicesX, 12 + indicesY, 14 + indicesZ,

                16 + indicesX, 17 + indicesY, 19 + indicesZ,
                19 + indicesX, 16 + indicesY, 18 + indicesZ,

                20 + indicesX, 21 + indicesY, 23 + indicesZ,
                23 + indicesX, 20 + indicesY, 22 + indicesZ,
        };
    }

    public double getDistPlayer(Entity player) {
        return Math.sqrt(Math.pow(position.x - player.getCamera().getPosition().x, 2)
                + Math.pow(position.y - player.getCamera().getPosition().y, 2)
                + Math.pow(position.z - player.getCamera().getPosition().z, 2));
    }

    public static Texture getTexture() {
        return texture;
    }

    public float[] getVerticesFull() {
        return verticesFull;
    }

    public float[] getVerticesPos() {
        return verticesPos;
    }

    public int[] getIndices() {
        return indices;
    }

    public float[] getTcs() {
        return tcs;
    }

    public Vector3f getPosition() {
        return position;
    }

    public boolean isShowing() {
        return showing;
    }

    public void setShowing(boolean showing) {
        this.showing = showing;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
