package Client.Game.Interaction.Events;

import Client.Engine.Graphics.Mesh;
import Client.Game.Entity.Player;
import Client.Game.Interaction.IBlockModificator;
import Client.Game.Interaction.IRay;
import Client.Game.World.Block.Block;
import Client.Game.World.Block.BlockFace;
import Client.Game.World.Block.Stone;
import Client.Game.World.Chunk.Chunk;

import java.util.Map;

public final class BlockEvent {

    private BlockEvent() {
    }

    public static void blockRemoveEvent(Map<Chunk, Mesh> chunkMeshMap, Player player, Block minDistBlock, Chunk chunk) {
        player.setRemovedBlock(IBlockModificator.blockRemove(chunk, minDistBlock));
        if (player.getRemovedBlock() != null) {
            chunk.faceCull(minDistBlock);
            chunk.updateChunkMeshMap(chunkMeshMap);
        }
    }

    public static void blockAddEvent(Map<Chunk, Mesh> chunkMeshMap, Player player, Block minDistBlock, Chunk chunk) {
        boolean summonBlock = false;
        Stone stone = new Stone();
        BlockFace rayCastedFace = IRay.createRayToFaces(player, minDistBlock);
        if (rayCastedFace != null) {
            if (rayCastedFace.isSelected()) {
                summonBlock = true;
                switch (rayCastedFace) {
                    case TOP:
                        stone.getPosition().set(minDistBlock.getPosition().x, minDistBlock.getPosition().y - 1f, minDistBlock.getPosition().z);
                        break;
                    case BOTTOM:
                        stone.getPosition().set(minDistBlock.getPosition().x, minDistBlock.getPosition().y + 1f, minDistBlock.getPosition().z);
                        break;
                    case RIGHT:
                        stone.getPosition().set(minDistBlock.getPosition().x - 1f, minDistBlock.getPosition().y, minDistBlock.getPosition().z);
                        break;
                    case LEFT:
                        stone.getPosition().set(minDistBlock.getPosition().x + 1f, minDistBlock.getPosition().y, minDistBlock.getPosition().z);
                        break;
                    case FRONT:
                        stone.getPosition().set(minDistBlock.getPosition().x, minDistBlock.getPosition().y, minDistBlock.getPosition().z + 1f);
                        break;
                    case BACK:
                        stone.getPosition().set(minDistBlock.getPosition().x, minDistBlock.getPosition().y, minDistBlock.getPosition().z - 1f);
                        break;
                }
            }
        }
        if (summonBlock) {
            IBlockModificator.blockAdd(chunk, stone);
            player.setAddedBlock(stone);
            chunk.updateShowingBlocks();
            chunk.updateChunkMeshMap(chunkMeshMap);
        }
    }
}
