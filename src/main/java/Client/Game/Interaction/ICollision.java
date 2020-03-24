package Client.Game.Interaction;

import Client.Engine.Graphics.Mesh;
import Client.Game.Entity.Entity;
import Client.Game.Interaction.Events.BlockFaceEvent;
import Client.Game.World.Block.Block;
import Client.Game.World.Block.BlockFace;
import Client.Game.World.Chunk.Chunk;
import org.joml.AABBf;
import org.joml.Vector3f;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static Client.Game.Entity.Entity.*;
import static Client.Game.World.Block.Block.*;
import static Client.Game.World.Chunk.Chunk.CHUNK_DETECTION_RANGE;

public interface ICollision {

    Vector3f max = new Vector3f();
    Vector3f min = new Vector3f();

    Vector3f minEntity = new Vector3f();
    Vector3f maxEntity = new Vector3f();

    AABBf blockAABB = new AABBf();
    AABBf entityAABB = new AABBf();

    static void collisionAABB(Map<Chunk, Mesh> chunkMeshMap, Entity entity) {
        Predicate<BlockFace> predicate = blockFace -> blockFace.equals(BlockFace.TOP);
        for (var chunk : chunkMeshMap.keySet()) {
            if (chunk.getPosition().x - CHUNK_DETECTION_RANGE < entity.getCamera().getPosition().x
                    && chunk.getPosition().x + CHUNK_DETECTION_RANGE > entity.getCamera().getPosition().x
                    && chunk.getPosition().z - CHUNK_DETECTION_RANGE < entity.getCamera().getPosition().z
                    && chunk.getPosition().z + CHUNK_DETECTION_RANGE > entity.getCamera().getPosition().z) {
                for (int i = 0; i < chunk.getBlocks().size(); i++) {
                    Block block = chunk.getBlocks().get(i);
                    if (block != null) {
                        //to test just the TOP of the block
                        if (block.isShowing() && testAABB(entity, block)) {
                            BlockFace topFace = BlockFaceEvent.populateBlockFaces(entity, block).stream().filter(predicate).collect(Collectors.toUnmodifiableList()).get(0);
                            collisionResponse(entity, topFace);
                        }
                        //everything else than TOP of the block AND MUST BE BIGGER THAN PLAYER POS Y BCS YOU WANT TO TEST BLOCKS ABOVE YOU
                        if (block.isShowing() && testAABB(entity, block) && block.getPosition().y > entity.getCamera().getPosition().y) {
                            List<BlockFace> faces = BlockFaceEvent.populateBlockFaces(entity, block).stream().filter(predicate.negate()).collect(Collectors.toUnmodifiableList());
                            faces.forEach(blockFace -> blockFace.setDistanceToEntity(entity));
                            Optional<BlockFace> optionalBlockFace = faces.stream().min(Comparator.comparingDouble(BlockFace::getDistance));

                            if (optionalBlockFace.isEmpty()) return;
                            BlockFace closestFaceToPlayer = optionalBlockFace.get();

                            collisionResponse(entity, closestFaceToPlayer);
                        }
                    }
                }
            }
        }
    }

    private static void collisionResponse(Entity entity, BlockFace blockFace) {
        switch(blockFace){
            case TOP:
                entity.getCamera().getPosition().set(entity.getCamera().getPosition().add(0, entity.getVerticalSpeed(), 0));
                entity.setVerticalSpeed(0);
                break;
            case BOTTOM:
                entity.getCamera().getPosition().set(entity.getCamera().getPosition().add(0, entity.getVerticalSpeed(), 0));
                entity.setVerticalSpeed(0.15f);
                break;
            case LEFT:
                entity.getCamera().getPosition().set(entity.getCamera().getPosition().add(entity.getHorizontalSpeed(), 0, 0));
                break;
            case RIGHT:
                entity.getCamera().getPosition().set(entity.getCamera().getPosition().add(-entity.getHorizontalSpeed(), 0, 0));
                break;
            case FRONT:
                entity.getCamera().getPosition().set(entity.getCamera().getPosition().add(0, 0, entity.getHorizontalSpeed()));
                break;
            case BACK:
                entity.getCamera().getPosition().set(entity.getCamera().getPosition().add(0, 0, -entity.getHorizontalSpeed()));
                break;
        }
    }

    private static boolean testAABB(Entity entity, Block block) {
        min.set(-BLOCK_WIDTH, -BLOCK_HEIGHT, -BLOCK_DEPTH);
        max.set(BLOCK_WIDTH, BLOCK_HEIGHT, BLOCK_DEPTH);
        min.add(block.getPosition());
        max.add(block.getPosition());

        minEntity.set(-ENTITY_WIDTH, -ENTITY_HEIGHT, -ENTITY_DEPTH);
        maxEntity.set(ENTITY_WIDTH, ENTITY_HEIGHT, ENTITY_DEPTH);
        minEntity.add(entity.getCamera().getPosition());
        maxEntity.add(entity.getCamera().getPosition());

        entityAABB.setMin(minEntity);
        entityAABB.setMax(maxEntity);

        blockAABB.setMin(min);
        blockAABB.setMax(max);

        return entityAABB.testAABB(blockAABB);
    }
}
