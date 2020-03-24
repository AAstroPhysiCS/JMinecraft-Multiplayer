package Client.Game.World.Block;

import Client.Game.World.Chunk.Chunk;

public class Stone extends Block {

    public static final int STONE_DEPTH = Chunk.CHUNK_Y;

    @Override
    protected void setTcs() {
        tcs = new float[]{
                0.016f, 0f,
                0.031f, 0f,
                0.016f, 0.015f,
                0.031f, 0.015f,

                0.016f, 0f,
                0.031f, 0f,
                0.016f, 0.015f,
                0.031f, 0.015f,

                0.016f, 0f,
                0.031f, 0f,
                0.016f, 0.015f,
                0.031f, 0.015f,

                0.016f, 0f,
                0.031f, 0f,
                0.016f, 0.015f,
                0.031f, 0.015f,

                0.016f, 0f,
                0.031f, 0f,
                0.016f, 0.015f,
                0.031f, 0.015f,

                0.016f, 0f,
                0.031f, 0f,
                0.016f, 0.015f,
                0.031f, 0.015f,
        };
    }
}
