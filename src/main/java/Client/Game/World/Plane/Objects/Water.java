package Client.Game.World.Plane.Objects;

import Client.Game.World.Block.WaterBlock;
import org.joml.Vector3f;

public class Water extends WorldObject {

    private static final int WATER_BLOCK_AMOUNT = 16;
    public static final int WATER_HEIGHT = 5;

    public Water() {}

    @Override
    public void create(Vector3f position) {
        this.position = position;
        for (int x = 0; x < Math.sqrt(WATER_BLOCK_AMOUNT); x++) {
            for (int z = 0; z < Math.sqrt(WATER_BLOCK_AMOUNT); z++) {
                allBlocks.add(new WaterBlock(new Vector3f(position.x + x, -30, position.z + z)));
            }
        }
    }
}
