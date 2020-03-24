package Client.Game.Interaction;

import Client.Engine.Graphics.Mesh;
import Client.Game.Entity.Entity;
import Client.Game.Interaction.Events.BlockFaceEvent;
import Client.Game.World.Block.Block;
import Client.Game.World.Block.BlockFace;
import Client.Game.World.Chunk.Chunk;
import org.joml.Intersectionf;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.*;

import static Client.Game.World.Block.Block.*;
import static Client.Game.World.Chunk.Chunk.CHUNK_DETECTION_RANGE;

public interface IRay {

    Vector3f max = new Vector3f();
    Vector3f min = new Vector3f();
    Vector2f result = new Vector2f();

    static void createRayToBlock(Map<Chunk, Mesh> chunkMeshMap, Entity entity) {
        reset();
        float highestDistance = Float.POSITIVE_INFINITY;
        for (var chunk : chunkMeshMap.keySet()) {
            if (chunk.getPosition().x - CHUNK_DETECTION_RANGE < entity.getCamera().getPosition().x
                    && chunk.getPosition().x + CHUNK_DETECTION_RANGE > entity.getCamera().getPosition().x
                    && chunk.getPosition().z - CHUNK_DETECTION_RANGE < entity.getCamera().getPosition().z
                    && chunk.getPosition().z + CHUNK_DETECTION_RANGE > entity.getCamera().getPosition().z) {
                int count = 0;
                int blockSize = 0;
                for (int i = 0; i < chunk.getBlocks().size(); i++) {
                    Block block = chunk.getBlocks().get(i);
                    if (block != null) {
                        if (block.isShowing()) {
                            blockSize++;
                            min.set(-BLOCK_WIDTH, -BLOCK_HEIGHT, -BLOCK_DEPTH);
                            max.set(BLOCK_WIDTH, BLOCK_HEIGHT, BLOCK_DEPTH);
                            min.add(block.getPosition());
                            max.add(block.getPosition());
                            if (Intersectionf.intersectRayAab(entity.getCamera().getPosition(), entity.getViewDirection(), min, max, result) && result.x < highestDistance) {
                                block.setSelected(true);
                                chunk.setChunkBlockSelected(true);
                                result.x = highestDistance;
                            } else {
                                block.setSelected(false);
                                count++;
                                if (count == blockSize) {
                                    chunk.setChunkBlockSelected(false);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    static BlockFace createRayToFaces(Entity entity, Block block) {
        reset();

        float highestDistance = Float.POSITIVE_INFINITY;
        List<BlockFace> faces = BlockFaceEvent.populateBlockFaces(entity, block);
        Optional<BlockFace> optionalBlockFace = faces.stream().min(Comparator.comparingDouble(BlockFace::getDistance));

        if(optionalBlockFace.isEmpty()) return null;
        BlockFace closestFaceToPlayer = optionalBlockFace.get();

        min.set(-BLOCK_WIDTH, -BLOCK_HEIGHT, -BLOCK_DEPTH);
        max.set(BLOCK_WIDTH, BLOCK_HEIGHT, BLOCK_DEPTH);
        min.add(closestFaceToPlayer.getPos());
        max.add(closestFaceToPlayer.getPos());
        if (Intersectionf.intersectRayAab(entity.getCamera().getPosition(), entity.getViewDirection(), min, max, result) && result.x < highestDistance) {
            closestFaceToPlayer.setSelected(true);
            result.x = highestDistance;
            return closestFaceToPlayer;
        }
        return null;
    }

    private static void reset() {
        min.zero();
        max.zero();
        result.zero();
    }
}
