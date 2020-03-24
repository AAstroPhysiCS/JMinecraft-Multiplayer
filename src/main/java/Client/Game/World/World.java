package Client.Game.World;

import Client.Engine.Graphics.Mesh;
import Client.Engine.Graphics.Shader;
import Client.Game.Entity.Player;
import Client.Game.Interaction.IBlockModificator;
import Client.Game.Interaction.IFrustumCuller;
import Client.Game.World.Block.Block;
import Client.Game.World.Chunk.Chunk;
import Client.Game.World.Chunk.IChunkModifier;
import Client.Network.ConnectedPlayer;
import Server.ConnectedClient;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static Client.Game.World.Chunk.Chunk.CHUNK_SIZE;

public final class World implements IWorldConstants {

    private static final Map<Chunk, Mesh> chunkMeshMap = new ConcurrentHashMap<>();

    private static final ExecutorService executor = Executors.newFixedThreadPool(2);

    private Shader shader;
    private Player player;

    private List<ConnectedPlayer> connectedPlayers = new ArrayList<>();

    private static Block oldAddedBlock = null, oldRemovedBlock = null;

    public World() {
        //waiting for chunk information from server
        shader = new Shader("shaders/world.vs", "shaders/world.fs");
        player = new Player(0.15f, new Vector3f((float) WORLD_CHUNK_SIZE/2, -70, (float) WORLD_CHUNK_SIZE/2));

        new ScheduledThreadPoolExecutor(1).scheduleAtFixedRate(IChunkModifier.setChunks(WorldSetting.INFINITE, chunkMeshMap, player), 0, (long) (player.getHorizontalSpeed()*100), TimeUnit.MILLISECONDS); // bcs it depends on the player speed
        Block.loadTexture();
        Player.loadTexture();

        System.out.println(WORLD_CHUNK_SIZE + " Chunks are loaded!");
        System.out.println("Total Block Size: " + WORLD_CHUNK_SIZE * 16 * 16 * 16);
    }

    public void setConnectedPlayers(List<ConnectedClient> clients) {
        for (ConnectedClient client : clients) {
            Vector3f pos = client.getPos();
            byte id = client.getId();
            Block removedBlock = client.getRemovedBlock();
            Block addedBlock = client.getAddedBlock();

            if (connectedPlayers.size() > 0) {
                int counter = 0;
                for (int j = 0; j < connectedPlayers.size(); j++) {
                    if (connectedPlayers.get(j).getId() != id) {
                        counter++;
                        if (counter == connectedPlayers.size())
                            connectedPlayers.add(new ConnectedPlayer(pos, id));
                    } else {
                        connectedPlayers.get(j).getCamera().getPosition().set(pos);

                        if (oldRemovedBlock == null)
                            connectedPlayers.get(j).setRemovedBlock(removedBlock);
                        else {
                            if (!oldRemovedBlock.getPosition().equals(removedBlock.getPosition()))
                                connectedPlayers.get(j).setRemovedBlock(removedBlock);
                        }

                        if (oldAddedBlock == null)
                            connectedPlayers.get(j).setAddedBlock(addedBlock);
                        else {
                            if (!oldAddedBlock.getPosition().equals(addedBlock.getPosition()))
                                connectedPlayers.get(j).setAddedBlock(addedBlock);
                        }

                        oldRemovedBlock = removedBlock;
                        oldAddedBlock = addedBlock;
                    }
                }
            }

            if (connectedPlayers.size() == 0)
                connectedPlayers.add(new ConnectedPlayer(pos, id));
        }
        setChunkBlockUpdates(connectedPlayers);
    }

    public void setChunkBlockUpdates(List<ConnectedPlayer> players) {
        chunkAddBlockUpdate(players);
        chunkRemoveBlockUpdate(players);
    }

    private void chunkRemoveBlockUpdate(List<ConnectedPlayer> players) {
        outer:
        for (ConnectedPlayer connectedPlayer : players) {
            Block removedBlock = connectedPlayer.getRemovedBlock();
            if (removedBlock != null) {
                Predicate<Block> predicate = block -> block.getPosition().equals(removedBlock.getPosition());
                for (Chunk chunk : chunkMeshMap.keySet()) {
                    boolean mainListSucc = chunk.getBlocks().removeIf(predicate);
                    List<Block> removedByAnotherPlayer = chunk.getShowingsBlocks().stream().filter(predicate).collect(Collectors.toList());
                    if (mainListSucc && removedByAnotherPlayer.size() > 0) {
                        chunk.faceCull(removedByAnotherPlayer.get(0));
                        chunk.updateChunkMeshMap(chunkMeshMap);
                        connectedPlayer.setRemovedBlock(null);
                        break outer;
                    }
                }
            }
        }
    }

    private void chunkAddBlockUpdate(List<ConnectedPlayer> players) {
        outer:
        for (ConnectedPlayer connectedPlayer : players) {
            Block addedBlock = connectedPlayer.getAddedBlock();
            if (addedBlock != null) {
                for (Chunk chunk : chunkMeshMap.keySet()) {
                    if (chunk.getPosition().x > addedBlock.getPosition().x && chunk.getPosition().x - CHUNK_SIZE < addedBlock.getPosition().x &&
                            chunk.getPosition().z > addedBlock.getPosition().z && chunk.getPosition().z - CHUNK_SIZE < addedBlock.getPosition().z && !chunk.getBlocks().contains(addedBlock)) {
                        IBlockModificator.blockAdd(chunk, addedBlock);
                        chunk.updateShowingBlocks();
                        chunk.updateChunkMeshMap(chunkMeshMap);
                        connectedPlayer.setAddedBlock(null);
                        break outer;
                    }
                }
            }
        }
    }

    public void update() {
        player.update();
        player.collisionDetection(chunkMeshMap);
    }

    public void render() {
        shader.enable();
        shader.setUniform1i("tex", 1);
        shader.setViewMatrix(player.getCamera().getPosition(), player.getCamera().getRotation());

        executor.execute(player.ray(chunkMeshMap, shader.getViewMatrix()));

        IFrustumCuller.filterFrustum(chunkMeshMap.keySet(), shader.getViewMatrix());

        Block.getTexture().bind();
        for (var chunkMesh : chunkMeshMap.entrySet()) {
            Chunk chunk = chunkMesh.getKey();
            if (player.closeToChunk(chunk) && chunk.isInsideFrustum()) {
                Mesh mesh = chunkMesh.getValue();
                if (chunk.isChunkBlockSelected()) {
                    shader.setUniform1i("selected", 1);
                    executor.execute(chunk.updatePlayerInteractions(chunkMeshMap, player));
                    Chunk.renderSelectedBlocks(chunk.getAllSelectedBlocks());
                    shader.setUniform1i("selected", 0);
                }
                if (mesh.empty())
                    mesh.load();
                mesh.render();
            }
        }
        Block.getTexture().unbind();

        Player.getTexture().bind();
        for (ConnectedPlayer connectedPlayer : connectedPlayers) {
            connectedPlayer.render();
        }
        Player.getTexture().unbind();

        shader.disable();
    }

    public Player getPlayer() {
        return player;
    }
}
