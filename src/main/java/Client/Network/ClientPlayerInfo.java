package Client.Network;

import Client.Game.World.Block.Block;
import org.joml.Vector3f;

import java.io.Serializable;

public class ClientPlayerInfo implements Serializable {

    //data class => record

    private Vector3f pos;
    private byte id;
    private Block removedBlock, addedBlock;

    public ClientPlayerInfo(Vector3f pos, byte id, Block removedBlock, Block addedBlock){
        this.pos = pos;
        this.id = id;
        this.removedBlock = removedBlock;
        this.addedBlock = addedBlock;
    }

    public Vector3f getPos() {
        return pos;
    }

    public byte getId() {
        return id;
    }

    public Block getRemovedBlock() {
        return removedBlock;
    }

    public Block getAddedBlock() {
        return addedBlock;
    }
}
