package Client.Utils;

import Client.Engine.Graphics.Mesh;
import Client.Game.World.Block.Block;
import Client.Game.World.Plane.Objects.WorldObject;

import java.util.List;

public final class MeshConfigurator {

    public static <T extends Block> Mesh createMesh(List<T> blocks) {
        float[] verticesAll = new float[blocks.size() * 24 * 4];
        int[] indicesAll = new int[blocks.size() * 12 * 3];
        float[] tcsAll = new float[blocks.size() * 24 * 2];

        for (Block block : blocks) {
            block.createVertices();
        }

        for (int i = 0; i < blocks.size(); i++) {
            blocks.get(i).createIndices(i * 24, i * 24, i * 24);
        }

        int verticesPointer = 0, indicesPointer = 0, tcsPointer = 0;
        for (T value : blocks) {
            for (int j = 0; j < value.getVerticesFull().length; j++) {
                verticesAll[verticesPointer++] = value.getVerticesFull()[j];
            }
            for (int j = 0; j < value.getIndices().length; j++) {
                indicesAll[indicesPointer++] = value.getIndices()[j];
            }
            for (int j = 0; j < value.getTcs().length; j++) {
                tcsAll[tcsPointer++] = value.getTcs()[j];
            }
        }
        return new Mesh(verticesAll, indicesAll, tcsAll);
    }

    public static <T extends WorldObject> Mesh createMesh(T[] worldObj, int sizeOfListBlocks) {
        float[] verticesAll = new float[worldObj.length * 24 * 4 * sizeOfListBlocks];
        int[] indicesAll = new int[worldObj.length * 12 * 3 * sizeOfListBlocks];
        float[] tcsAll = new float[worldObj.length * 24 * 2 * sizeOfListBlocks];

        int verticesPointer = 0, indicesPointer = 0, tcsPointer = 0;
        for (int i = 0; i < worldObj.length; i++) {
            for (int j = 0; j < worldObj[i].getAllBlocks().size(); j++) {
                int off = i * worldObj[i].getAllBlocks().size() * 24;
                Block block = worldObj[i].getAllBlocks().get(j);
                block.createVertices();
                block.createIndices(off + (j * 24), off + (j * 24), off + (j * 24));
                for (int k = 0; k < block.getVerticesFull().length; k++) {
                    verticesAll[verticesPointer++] = block.getVerticesFull()[k];
                }
                for (int k = 0; k < block.getIndices().length; k++) {
                    indicesAll[indicesPointer++] = block.getIndices()[k];
                }
                for (int k = 0; k < block.getTcs().length; k++) {
                    tcsAll[tcsPointer++] = block.getTcs()[k];
                }
            }
        }
        return new Mesh(verticesAll, indicesAll, tcsAll);
    }
}
