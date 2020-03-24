package Client.Game.World.Block;

import org.joml.Vector3f;

public class WaterBlock extends Block {

    public WaterBlock(Vector3f pos){
        this.position = pos;
    }

    @Override
    public void createVertices() {
        verticesFull = new float[]{
                //front
                -BLOCK_WIDTH + position.x, -BLOCK_HEIGHT + position.y, BLOCK_DEPTH + position.z, BlockFace.TOP.getBrightness(),
                BLOCK_WIDTH + position.x, -BLOCK_HEIGHT + position.y, BLOCK_DEPTH + position.z, BlockFace.TOP.getBrightness(),
                -BLOCK_WIDTH + position.x, -BLOCK_HEIGHT + position.y, -BLOCK_DEPTH + position.z, BlockFace.TOP.getBrightness(),
                BLOCK_WIDTH + position.x, -BLOCK_HEIGHT + position.y, -BLOCK_DEPTH + position.z, BlockFace.TOP.getBrightness(),
        };

        verticesPos = new float[]{
                //front
                -BLOCK_WIDTH + position.x, -BLOCK_HEIGHT + position.y, BLOCK_DEPTH + position.z,
                BLOCK_WIDTH + position.x, -BLOCK_HEIGHT + position.y, BLOCK_DEPTH + position.z,
                -BLOCK_WIDTH + position.x, -BLOCK_HEIGHT + position.y, -BLOCK_DEPTH + position.z,
                BLOCK_WIDTH + position.x, -BLOCK_HEIGHT + position.y, -BLOCK_DEPTH + position.z,
        };
    }

    @Override
    protected void setTcs() {
        tcs = new float[]{
                0.129f, 0.177f,
                0.143f, 0.177f,
                0.129f, 0.191f,
                0.143f, 0.191f,
        };
    }
}
