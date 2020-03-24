package Client.Game.World.Plane;

import Client.Engine.Graphics.Mesh;
import Client.Game.World.Chunk.Chunk;
import Client.Game.World.IWorldConstants;
import Client.Game.World.Plane.Objects.Tree;

import java.util.Map;

public class WorldObjectPane {

    private static WorldObjectBuilder<Tree> treeWorldObjectBuilder;
//    private static WorldObjectBuilder<Water> waterWorldObjectBuilder; for later!

    private WorldObjectPane() {}

    public static void init(long seed){
        treeWorldObjectBuilder = new WorldObjectBuilder<>(Tree::new, IWorldConstants.TREE_SIZE, seed);
    }

    public static void spawn(Chunk chunk, Map<Chunk, Mesh> chunkMeshMap) {
        treeWorldObjectBuilder.init(chunk, chunkMeshMap);
    }
}
