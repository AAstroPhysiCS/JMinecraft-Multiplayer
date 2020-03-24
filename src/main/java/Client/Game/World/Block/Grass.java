package Client.Game.World.Block;

public class Grass extends Block {

    public static final int GRASS_DEPTH = 1;

    @Override
    protected void setTcs() {
        tcs = new float[] {
                0.063f, 0,
                0.048f, 0,
                0.063f, 0.015f,
                0.048f, 0.015f,

                0.063f, 0,
                0.048f, 0,
                0.063f, 0.015f,
                0.048f, 0.015f,

                0.063f, 0,
                0.048f, 0,
                0.063f, 0.015f,
                0.048f, 0.015f,

                0.063f, 0,
                0.048f, 0,
                0.063f, 0.015f,
                0.048f, 0.015f,

                0.145f, 0.177f,
                0.160f, 0.177f,
                0.145f, 0.191f,
                0.160f, 0.191f,

                0.047f, 0,
                0.032f, 0,
                0.047f, 0.015f,
                0.032f, 0.015f,
        };
    }
}
