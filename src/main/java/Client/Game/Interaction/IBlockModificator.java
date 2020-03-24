package Client.Game.Interaction;

import Client.Game.World.Block.Block;
import Client.Game.World.Chunk.Chunk;

import java.util.List;

public interface IBlockModificator {
    static Block blockRemove(Chunk chunk, Block block) {
        for (Block chunkBlocks : chunk.getBlocks()) {
            if (chunkBlocks.getPosition().equals(block.getPosition())) {
                chunk.getBlocks().remove(chunkBlocks);
                return chunkBlocks;
            }
        }
        return null;
    }

    static void blockAdd(Chunk chunk, Block desiredBlock) {
        desiredBlock.setShowing(true);
        chunk.getBlocks().add(0, desiredBlock);
    }

    static void blockAdd(Chunk chunk, List<Block> desiredBlockList) {
        desiredBlockList.forEach(block -> block.setShowing(true));
        chunk.getBlocks().addAll(0, desiredBlockList);
    }
}
