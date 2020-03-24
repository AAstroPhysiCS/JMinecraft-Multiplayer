package Client.Game.World.Chunk;

import Client.Engine.Graphics.Mesh;
import Client.Game.Entity.Entity;
import Client.Game.Entity.Player;
import Client.Game.World.Plane.WorldObjectPane;
import Client.Game.World.WorldSetting;
import Client.Network.Client;
import org.joml.Vector3f;

import java.util.Map;
import java.util.Random;

import static Client.Game.World.IWorldConstants.*;

public interface IChunkModifier {

    Vector3f oldPoint = new Vector3f();
    Random random = new Random();

    static Runnable setChunks(WorldSetting setting, Map<Chunk, Mesh> mapBuffer, Player player) {
        return () -> {
            WorldObjectPane.init(Client.seed + 234098723);
            if (setting.equals(WorldSetting.INFINITE))
                addChunkInfinite(mapBuffer, player);
            else
                addChunkNonInfinite(mapBuffer);
            removeDistantChunks(mapBuffer, player);
        };
    }

    private static void addChunkInfinite(Map<Chunk, Mesh> mapBuffer, Player player) {
        Vector3f pos = player.getCamera().getPosition();
        if (Client.seed > 0) {
            random.setSeed(Client.seed + 12230984);
            float viewDistanceX = pos.x - VIEW_DISTANCE;
            float viewDistanceZ = pos.z - VIEW_DISTANCE;

            if (mapBuffer.size() == 0) {
                for (int x = 0; x < Math.sqrt(WORLD_CHUNK_SIZE); x++) {
                    for (int z = 0; z < Math.sqrt(WORLD_CHUNK_SIZE); z++) {
                        Chunk chunk = new Chunk();
                        chunk.getPosition().set(Math.round((16 * (x + 1)) + viewDistanceX), 0, Math.round((16 * (z + 1)) + viewDistanceZ));
                        chunk.getPosition().set(validate(chunk.getPosition()));
                        chunk.setBlocks();

                        IChunkMapIterator.add(chunk, mapBuffer, chunk.getShowingsBlocks());
                        if (random.nextInt() > 0.5f && chunk.worldObjectSize < 2) {
                            WorldObjectPane.spawn(chunk, mapBuffer);
                        }
                    }
                }
            }

            if (Math.round(oldPoint.x) == Math.round(pos.x + CHUNK_LOAD_DISTANCE)
                    || Math.round(oldPoint.z) == Math.round(pos.z + CHUNK_LOAD_DISTANCE)
                    || Math.round(oldPoint.x) == Math.round(pos.x - CHUNK_LOAD_DISTANCE)
                    || Math.round(oldPoint.z) == Math.round(pos.z - CHUNK_LOAD_DISTANCE) && mapBuffer.size() > 0) {

                for (int x = 0; x < Math.sqrt(WORLD_CHUNK_SIZE); x++) {
                    for (int z = 0; z < Math.sqrt(WORLD_CHUNK_SIZE); z++) {
                        Chunk chunk = new Chunk();
                        chunk.getPosition().set(Math.round((16 * (x + 1)) + (viewDistanceX)), 0, Math.round((16 * (z + 1)) + (viewDistanceZ)));
                        chunk.getPosition().set(validate(chunk.getPosition()));
                        chunk.setBlocks();

                        int count = 0;
                        for (Chunk allChunks : mapBuffer.keySet()) {
                            if (!allChunks.getPosition().equals(chunk.getPosition())) {
                                count++;
                                if (count == mapBuffer.keySet().size() - 1) {
                                    IChunkMapIterator.add(chunk, mapBuffer, chunk.getShowingsBlocks());
                                    if (random.nextInt() > 0.5f && chunk.worldObjectSize < 2) {
                                        WorldObjectPane.spawn(chunk, mapBuffer);
                                    }
                                }
                            }
                        }
                    }
                }
                oldPoint.set(pos);
            }
        } else {
            oldPoint.set(pos); // beginning of game, means that i have to update the oldpoint
        }
    }

    private static void removeDistantChunks(Map<Chunk, Mesh> mapBuffer, Entity entity){
        for(Chunk chunk : mapBuffer.keySet()){
            if(!entity.closeToChunk(chunk)){
                mapBuffer.remove(chunk);
            }
        }
    }

    //for aligning!, so that every chunk can be dividable by 16 without any remainder
    private static Vector3f validate(Vector3f pos) {
        float modX = pos.x % 16;
        if (modX != 0) {
            float hope1 = pos.x + modX;
            if (hope1 % 16 == 0)
                pos.add(modX, 0, 0);
            else
                pos.add(-modX, 0, 0);
        }
        float modZ = pos.z % 16;
        if (modZ != 0) {
            float hope1 = pos.z + modZ;
            if (hope1 % 16 == 0)
                pos.add(0, 0, modZ);
            else
                pos.add(0, 0, -modZ);
        }
        return pos;
    }

    private static void addChunkNonInfinite(Map<Chunk, Mesh> meshBuffer) {
        if (Client.seed > 0) {
            //plus a random typed number via my fingers
            Random random = new Random(Client.seed + 12230984);
            WorldObjectPane.init(Client.seed + 234098723);

            for (int x = 0; x < Math.round(Math.sqrt(WORLD_CHUNK_SIZE)); x++) {
                for (int z = 0; z < Math.round(Math.sqrt(WORLD_CHUNK_SIZE)); z++) {
                    Chunk chunk = new Chunk();
                    chunk.getPosition().set(16 * x, 0, 16 * z);
                    chunk.setBlocks();
                    IChunkMapIterator.add(chunk, meshBuffer, chunk.getShowingsBlocks());
                    //random world object spawn
                    if (random.nextInt() > 0.5f)
                        WorldObjectPane.spawn(chunk, meshBuffer);
                }
            }
            try {
                Thread.currentThread().join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
