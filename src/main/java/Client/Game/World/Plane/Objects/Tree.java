package Client.Game.World.Plane.Objects;

import Client.Game.World.Block.Bush;
import Client.Game.World.Block.Wood;
import org.joml.Vector3f;

public class Tree extends WorldObject {

    public static final int TREE_HEIGHT = 3, BUSH_WIDTH = 5, BUSH_HEIGHT = 3, BUSH_DEPTH = 5;

    public Tree() {}

    public void create(Vector3f pos){
        this.position = pos;
        for(int i = 0; i < TREE_HEIGHT; i++)
            allBlocks.add(new Wood(new Vector3f(position.x + 2f, i + position.y, position.z + 2f)));
        for(int x = 0; x < BUSH_WIDTH; x++){
            for(int y = 0; y < BUSH_HEIGHT; y++){
                for(int z = 0; z < BUSH_DEPTH; z++){
                    allBlocks.add(new Bush(new Vector3f(x + position.x, -TREE_HEIGHT + y + position.y, z + position.z)));
                }
            }
        }
    }
}
