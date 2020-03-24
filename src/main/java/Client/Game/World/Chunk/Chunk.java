package Client.Game.World.Chunk;

import Client.Engine.Graphics.Mesh;
import Client.Events.MouseEvent;
import Client.Game.Entity.Entity;
import Client.Game.Entity.Player;
import Client.Game.Interaction.IBlockModificator;
import Client.Game.Interaction.IFaceCuller;
import Client.Game.World.Block.Block;
import Client.Game.World.Block.Dirt;
import Client.Game.World.Block.Grass;
import Client.Game.World.Block.Stone;
import Client.Game.World.Plane.Objects.WorldObject;
import Client.Game.World.Utils.PerlinNoise;
import Client.Utils.MeshConfigurator;
import org.joml.Vector3f;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_2;
import static org.lwjgl.opengl.GL11.*;

public class Chunk {

    public static final int CHUNK_Y = 16;  //for custom height
    public static final int CHUNK_SIZE = 16;
    private static final int CHUNK_VOLUME = CHUNK_SIZE * CHUNK_Y * CHUNK_SIZE;

    public int worldObjectSize = 0;

    public static final int CHUNK_DETECTION_RANGE = 24;

    private final List<Block> blocks = new CopyOnWriteArrayList<>();
    private List<Block> showingsBlocks = new ArrayList<>();
    private List<Block> allSelectedBlocks = new ArrayList<>();

    private boolean isBlockSelected;

    private static boolean mouseButton1Clicked = false, mouseButton2Clicked = false;

    private boolean insideFrustum;

    private final Vector3f position = new Vector3f();

    public Chunk() {
        for (int i = 0; i < CHUNK_VOLUME; i++) {
            if (i < 16 * 16 * Grass.GRASS_DEPTH)
                blocks.add(new Grass());
            else if (i < 16 * 16 * (Grass.GRASS_DEPTH + Dirt.DIRT_DEPTH))
                blocks.add(new Dirt());
            else if (i < 16 * 16 * Stone.STONE_DEPTH)
                blocks.add(new Stone());
        }
    }

    public void addWorldObjects(Map<Chunk, Mesh> chunkMeshMap, List<WorldObject> worldObjects) {
        for (WorldObject object : worldObjects) {
            IBlockModificator.blockAdd(this, object.getAllBlocks());
        }
        worldObjectSize += worldObjects.size();
        this.updateShowingBlocks();
        this.updateChunkMeshMap(chunkMeshMap);
    }

    public void setBlocks() {
        int pointer = 0;

        pointer = layerize(pointer, 0, Grass.GRASS_DEPTH, Grass.class.getName(), true);
        pointer = layerize(pointer, Grass.GRASS_DEPTH, Grass.GRASS_DEPTH + Dirt.DIRT_DEPTH, Dirt.class.getName(), false);
        layerize(pointer, Grass.GRASS_DEPTH + Dirt.DIRT_DEPTH, Stone.STONE_DEPTH, Stone.class.getName(), false);

        updateShowingBlocks();
    }

    private int layerize(int pointer, int startPos, int endPos, String className, boolean isTopLayer) {
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int z = 0; z < CHUNK_SIZE; z++) {
                float xNew = x + position.x;
                float zNew = z + position.z;
                if (blocks.get(pointer).getClass().getName().equals(className)) {
                    for (int y = startPos; y < endPos; y++) {
                        blocks.get(pointer).getPosition().set(xNew, y + PerlinNoise.generateHeight(xNew, zNew), zNew);
                        if (y == 0 && isTopLayer)
                            blocks.get(pointer).setShowing(true);
                        pointer++;
                    }
                }
            }
        }
        return pointer;
    }

    public void faceCull(Block origin) {
        IFaceCuller.faceCull(blocks, origin);
        updateShowingBlocks();
    }

    public void updateShowingBlocks() {
        showingsBlocks = blocks
                .stream()
                .filter(Block::isShowing)
                .collect(Collectors.toList());
    }

    public static void renderSelectedBlocks(List<Block> blocks) {
        Mesh mesh = MeshConfigurator.createMesh(blocks);
        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        if (mesh.empty())
            mesh.load();
        mesh.render();
        glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
    }

    public Runnable updatePlayerInteractions(Map<Chunk, Mesh> chunkMeshMap, Player player) {
        return () -> {
            allSelectedBlocks = showingsBlocks
                    .stream()
                    .filter(Block::isSelected)
                    .filter(block -> block.getDistPlayer(player) < 5)
                    .collect(Collectors.toList());

            Optional<Block> optionalBlock = allSelectedBlocks.stream().min(Comparator.comparingDouble(o -> o.getDistPlayer(player)));
            if (optionalBlock.isEmpty()) return;
            Block minDistBlock = optionalBlock.get();

            if (MouseEvent.getButtons()[GLFW_MOUSE_BUTTON_1] && !mouseButton1Clicked) {
                player.blockRemoveEvent(chunkMeshMap, minDistBlock, this);
                mouseButton1Clicked = true;
            }
            if (!MouseEvent.getButtons()[GLFW_MOUSE_BUTTON_1] && mouseButton1Clicked) {
                mouseButton1Clicked = false;
            }

            if (MouseEvent.getButtons()[GLFW_MOUSE_BUTTON_2] && !mouseButton2Clicked) {
                player.blockAddEvent(chunkMeshMap, minDistBlock, this);
                mouseButton2Clicked = true;
            }

            if (!MouseEvent.getButtons()[GLFW_MOUSE_BUTTON_2] && mouseButton2Clicked) {
                mouseButton2Clicked = false;
            }
        };
    }

    public double calcDistanceToEntity(Entity entity){
        return Math.sqrt(Math.pow(this.position.x - entity.getCamera().getPosition().x, 2)
                + Math.pow(this.position.y - entity.getCamera().getPosition().y, 2)
                + Math.pow(this.position.z - entity.getCamera().getPosition().z, 2));
    }

    public void setInsideFrustum(boolean insideFrustum) {
        this.insideFrustum = insideFrustum;
    }

    public boolean isInsideFrustum() {
        return insideFrustum;
    }

    public void updateChunkMeshMap(Map<Chunk, Mesh> chunkMeshMap) {
        IChunkMapIterator.replace(this, chunkMeshMap, showingsBlocks);
    }

    public List<Block> getAllSelectedBlocks() {
        return allSelectedBlocks;
    }

    public List<Block> getShowingsBlocks() {
        return showingsBlocks;
    }

    public Vector3f getPosition() {
        return position;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public void setChunkBlockSelected(boolean blockSelected) {
        isBlockSelected = blockSelected;
    }

    public boolean isChunkBlockSelected() {
        return isBlockSelected;
    }
}
