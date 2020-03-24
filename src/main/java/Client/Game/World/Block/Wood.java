package Client.Game.World.Block;

import org.joml.Vector3f;

public class Wood extends Block {

    public Wood(Vector3f pos){
        this.position = pos;
    }

    @Override
    protected void setTcs() {
        tcs = new float[]{
                0.079f, 0.016f,
                0.064f, 0.016f,
                0.079f, 0.031f,
                0.064f, 0.031f,

                0.079f, 0.016f,
                0.064f, 0.016f,
                0.079f, 0.031f,
                0.064f, 0.031f,

                0.079f, 0.016f,
                0.064f, 0.016f,
                0.079f, 0.031f,
                0.064f, 0.031f,

                0.079f, 0.016f,
                0.064f, 0.016f,
                0.079f, 0.031f,
                0.064f, 0.031f,

                0.080f, 0.016f,
                0.095f, 0.016f,
                0.080f, 0.031f,
                0.095f, 0.031f,

                0.080f, 0.016f,
                0.095f, 0.016f,
                0.080f, 0.031f,
                0.095f, 0.031f,
        };
    }
}
