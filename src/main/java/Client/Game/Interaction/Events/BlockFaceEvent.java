package Client.Game.Interaction.Events;

import Client.Game.Entity.Entity;
import Client.Game.World.Block.Block;
import Client.Game.World.Block.BlockFace;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class BlockFaceEvent {

    private BlockFaceEvent() {}

    public static List<BlockFace> populateBlockFaces(Entity entity, Block block) {
        List<BlockFace> faces = new ArrayList<>();
        if (block.getVerticesPos().length > 12)
            populate(faces, entity, block);
        else
            //means that the block is just a front faced block -> water for example
            populate(faces, entity, block, 0, 12);
        return faces;
    }

    private static void populate(List<BlockFace> faces, Entity entity, Block block){
        for (int i = 0; i < BlockFace.values().length; i++) {
            var face = BlockFace.values()[i];
            face.setVertices(block.getVerticesPos(), 12 * i, 12 * (i + 1));
            float x = 0, y = 0, z = 0;
            for (int j = 0; j < face.getVertices().length; ) {
                x += face.getVertices()[j++] / 4f;
                y += face.getVertices()[j++] / 4f;
                z += face.getVertices()[j++] / 4f;
            }
            face.setPos(new Vector3f(x, y, z));
            face.setDistanceToEntity(entity);
            faces.add(face);
        }
    }

    private static void populate(List<BlockFace> faces, Entity entity, Block block, int startIndex, int endIndex){
        var face = BlockFace.values()[4];
        face.setVertices(block.getVerticesPos(), startIndex, endIndex);
        float x = 0, y = 0, z = 0;
        for (int j = 0; j < face.getVertices().length; ) {
            x += face.getVertices()[j++] / 4f;
            y += face.getVertices()[j++] / 4f;
            z += face.getVertices()[j++] / 4f;
        }
        face.setPos(new Vector3f(x, y, z));
        face.setDistanceToEntity(entity);
        faces.add(face);
    }
}
