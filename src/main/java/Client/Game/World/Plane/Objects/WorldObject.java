package Client.Game.World.Plane.Objects;

import Client.Game.World.Block.Block;
import Client.Game.World.Chunk.Chunk;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public abstract class WorldObject {

    protected Vector3f position;
    protected final List<Block> allBlocks = new ArrayList<>();

    public WorldObject(Vector3f pos) {
        this.position = pos;
    }

    public WorldObject() {
        position = new Vector3f(0, 0, 0);
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public List<Block> getAllBlocks() {
        return allBlocks;
    }

    public abstract void create(Vector3f position);

    public static float findY(Chunk chunk, float x, float z) {
        for (Block block : chunk.getBlocks()) {
            if (block.getPosition().x == x && block.getPosition().z == z) {
                return block.getPosition().y;
            }
        }
        return -1;
    }
}
