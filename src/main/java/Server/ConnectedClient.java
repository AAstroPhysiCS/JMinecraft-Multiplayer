package Server;

import Client.Game.World.Block.Block;
import org.joml.Vector3f;

import java.io.Serializable;

public class ConnectedClient implements Serializable {

    //data class... you can change this, when the java 14 comes out => record

    private byte id;
    private Vector3f pos;
    private Block removedBlock;
    private Block addedBlock;

    public ConnectedClient(Vector3f pos, byte id){
        this.pos = pos;
        this.id = id;
    }

    public void setRemovedBlock(Block removedBlock) {
        this.removedBlock = removedBlock;
    }

    public void setAddedBlock(Block addedBlock) {
        this.addedBlock = addedBlock;
    }

    public Block getRemovedBlock() {
        return removedBlock;
    }

    public Block getAddedBlock() {
        return addedBlock;
    }

    public byte getId() {
        return id;
    }

    public Vector3f getPos() {
        return pos;
    }

    public void setPos(Vector3f pos) {
        this.pos = pos;
    }

    public void setId(byte id) {
        this.id = id;
    }
}
