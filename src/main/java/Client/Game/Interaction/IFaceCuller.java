package Client.Game.Interaction;

import Client.Game.World.Block.Block;

import java.util.List;

public interface IFaceCuller {
    static void faceCull(List<Block> blocks, Block selectedBlockInRange) {
        for (int i = blocks.size() - 1; i >= 0; i--) {
            if (blocks.get(i).getPosition().y == selectedBlockInRange.getPosition().y && blocks.get(i).getPosition().z == selectedBlockInRange.getPosition().z && !blocks.get(i).isShowing()) {
                if (blocks.get(i).getPosition().x == selectedBlockInRange.getPosition().x + 1f) {
                    blocks.get(i).setShowing(true);
                }
                if (blocks.get(i).getPosition().x == selectedBlockInRange.getPosition().x - 1f) {
                    blocks.get(i).setShowing(true);
                }
            }
            if (blocks.get(i).getPosition().x == selectedBlockInRange.getPosition().x && blocks.get(i).getPosition().z == selectedBlockInRange.getPosition().z && !blocks.get(i).isShowing()) {
                if (blocks.get(i).getPosition().y == selectedBlockInRange.getPosition().y + 1f) {
                    blocks.get(i).setShowing(true);
                }
                if (blocks.get(i).getPosition().y == selectedBlockInRange.getPosition().y - 1f) {
                    blocks.get(i).setShowing(true);
                }
            }
            if (blocks.get(i).getPosition().x == selectedBlockInRange.getPosition().x && blocks.get(i).getPosition().y == selectedBlockInRange.getPosition().y && !blocks.get(i).isShowing()) {
                if (blocks.get(i).getPosition().z == selectedBlockInRange.getPosition().z + 1f) {
                    blocks.get(i).setShowing(true);
                }
                if (blocks.get(i).getPosition().z == selectedBlockInRange.getPosition().z - 1f) {
                    blocks.get(i).setShowing(true);
                }
            }
        }
    }
}
