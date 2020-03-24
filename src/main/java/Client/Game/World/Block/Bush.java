package Client.Game.World.Block;

import org.joml.Vector3f;

public class Bush extends Block {

    public Bush(Vector3f pos){
        this.position = pos;
    }

    @Override
    protected void setTcs() {
        tcs = new float[]{
                0.064f, 0.048f,
                0.080f, 0.048f,
                0.064f, 0.063f,
                0.080f, 0.063f,

                0.064f, 0.048f,
                0.080f, 0.048f,
                0.064f, 0.063f,
                0.080f, 0.063f,

                0.064f, 0.048f,
                0.080f, 0.048f,
                0.064f, 0.063f,
                0.080f, 0.063f,

                0.064f, 0.048f,
                0.080f, 0.048f,
                0.064f, 0.063f,
                0.080f, 0.063f,

                0.064f, 0.048f,
                0.080f, 0.048f,
                0.064f, 0.063f,
                0.080f, 0.063f,

                0.064f, 0.048f,
                0.080f, 0.048f,
                0.064f, 0.063f,
                0.080f, 0.063f,
        };
    }
}
