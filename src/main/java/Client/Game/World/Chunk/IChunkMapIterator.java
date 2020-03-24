package Client.Game.World.Chunk;

import Client.Engine.Graphics.Mesh;
import Client.Utils.MeshConfigurator;
import Client.Game.World.Block.Block;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public interface IChunkMapIterator extends Iterator<Map<Chunk, Mesh>> {
    static void add(Chunk chunk, Map<Chunk, Mesh> mapBuffer, List<Block> blockList) {
        Mesh mesh = MeshConfigurator.createMesh(blockList);
        mapBuffer.put(chunk, mesh);
    }

    static void replace(Chunk chunk, Map<Chunk, Mesh> mapBuffer, List<Block> blockList) {
        Mesh mesh = MeshConfigurator.createMesh(blockList);
        mapBuffer.replace(chunk, mesh);
    }
}
