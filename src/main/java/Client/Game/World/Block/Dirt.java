package Client.Game.World.Block;

public class Dirt extends Block {

    public static final int DIRT_DEPTH = 2;

    @Override
    protected void setTcs() {
        tcs = new float[] {
                0.047f, 0,
                0.032f, 0,
                0.047f, 0.015f,
                0.032f, 0.015f,

                0.047f, 0,
                0.032f, 0,
                0.047f, 0.015f,
                0.032f, 0.015f,

                0.047f, 0,
                0.032f, 0,
                0.047f, 0.015f,
                0.032f, 0.015f,

                0.047f, 0,
                0.032f, 0,
                0.047f, 0.015f,
                0.032f, 0.015f,

                0.047f, 0,
                0.032f, 0,
                0.047f, 0.015f,
                0.032f, 0.015f,

                0.047f, 0,
                0.032f, 0,
                0.047f, 0.015f,
                0.032f, 0.015f,
        };
    }
}
