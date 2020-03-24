package Client.Network;

import Client.Engine.Graphics.Mesh;
import Client.Game.Entity.Player;
import Client.Utils.BufferUtil;
import org.joml.Vector3f;

import java.nio.FloatBuffer;

public class ConnectedPlayer extends Player {

    private Mesh mesh;
    private byte id;

    private static FloatBuffer buffer;

    public ConnectedPlayer(Vector3f startPosition, byte id) {
        super(startPosition);
        mesh = new Mesh(vertices, indices, tcs);
        this.id = id;
    }

    public void render(){
        updateVertices();
        if(mesh.empty())
            mesh.loadWithoutData();
        buffer = BufferUtil.createFloatBuffer(vertices);
        mesh.renderSubData(buffer);
    }

    public byte getId() {
        return id;
    }
}
