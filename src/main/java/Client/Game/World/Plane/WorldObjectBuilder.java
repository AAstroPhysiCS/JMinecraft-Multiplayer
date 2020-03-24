package Client.Game.World.Plane;

import Client.Engine.Graphics.Mesh;
import Client.Game.World.Chunk.Chunk;
import Client.Game.World.Plane.Objects.Tree;
import Client.Game.World.Plane.Objects.Water;
import Client.Game.World.Plane.Objects.WorldObject;
import org.joml.Vector3f;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WorldObjectBuilder<T extends WorldObject> {

    private List<? extends T> listOfWorldObjects;
    private final Supplier<? extends T> supplier;
    private final int sizeOfObjects;
    private static Random rand;

    public WorldObjectBuilder(Supplier<? extends T> supplier, final int sizeOfObjects, final long seed) {
        this.supplier = supplier;
        this.sizeOfObjects = sizeOfObjects;
        rand = new Random(seed);
    }

    public void init(Chunk chunk, Map<Chunk, Mesh> chunkMeshMap) {
        listOfWorldObjects = Stream.generate(supplier).limit(sizeOfObjects).collect(Collectors.toUnmodifiableList());
        for (T worldObject : listOfWorldObjects) {
            float x = rand.nextInt((int) ((chunk.getPosition().x + 16f - chunk.getPosition().x) + 1)) + chunk.getPosition().x;
            float z = rand.nextInt((int) ((chunk.getPosition().z + 16f - chunk.getPosition().z) + 1)) + chunk.getPosition().z;
            float possibleY = WorldObject.findY(chunk, x, z);
            if (possibleY != -1 && worldObject instanceof Tree)
                worldObject.create(new Vector3f(x, possibleY - (Tree.TREE_HEIGHT - 1), z));
            if (possibleY != -1 && worldObject instanceof Water)
                worldObject.create(new Vector3f(x, possibleY - (Water.WATER_HEIGHT - 1), z));
        }
        if (listOfWorldObjects.stream().allMatch(o -> o != null && o.getPosition().y != 0)) //for safety
            chunk.addWorldObjects(chunkMeshMap, (List<WorldObject>) listOfWorldObjects);
    }
}
