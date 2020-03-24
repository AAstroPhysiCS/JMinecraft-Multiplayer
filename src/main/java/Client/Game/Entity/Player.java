package Client.Game.Entity;

import Client.Engine.Graphics.Mesh;
import Client.Events.KeyboardEvent;
import Client.Game.Interaction.Events.BlockEvent;
import Client.Game.Interaction.ICollision;
import Client.Game.Interaction.IRay;
import Client.Game.World.Block.Block;
import Client.Game.World.Chunk.Chunk;
import Client.Game.World.IWorldConstants;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.Map;

import static Client.Game.World.IWorldConstants.normalForce;
import static org.lwjgl.glfw.GLFW.*;

public class Player extends Entity {

    protected Block removedBlock;
    protected Block addedBlock;

    private static boolean spacePressed = false;

    public Player(float mouseSpeed, Vector3f startPosition) {
        super(mouseSpeed);
        camera.getPosition().set(startPosition);
        updateVertices();
        updateIndices();
        setTcs();
    }

    public Player(Vector3f startPosition) {
        camera.getPosition().set(startPosition);
        updateVertices();
        updateIndices();
        setTcs();
    }

    @Override
    public void update() {
        camera.enableControl();

        float angle90 = (float) (camera.getRotation().y + (Math.PI / 2));
        float angle = camera.getRotation().y;

        if (KeyboardEvent.getKeys()[GLFW_KEY_W]) {
            camera.getPosition().x -= Math.cos(angle90) * horizontalSpeed;
            camera.getPosition().z -= Math.sin(angle90) * horizontalSpeed;
        }
        if (KeyboardEvent.getKeys()[GLFW_KEY_S]) {
            camera.getPosition().x += Math.cos(angle90) * horizontalSpeed;
            camera.getPosition().z += Math.sin(angle90) * horizontalSpeed;
        }
        if (KeyboardEvent.getKeys()[GLFW_KEY_A]) {
            camera.getPosition().x += Math.cos(angle) * horizontalSpeed;
            camera.getPosition().z += Math.sin(angle) * horizontalSpeed;
        }
        if (KeyboardEvent.getKeys()[GLFW_KEY_D]) {
            camera.getPosition().x -= Math.cos(angle) * horizontalSpeed;
            camera.getPosition().z -= Math.sin(angle) * horizontalSpeed;
        }
        if (!floorState) {
            verticalSpeed -= IWorldConstants.gravity;
        }
        if (KeyboardEvent.getKeys()[GLFW_KEY_SPACE] && !spacePressed) {
            verticalSpeed += normalForce;   //normalForce = FN -> for this i had to attend to a fucken HTL for 3 years
            floorState = false;
            spacePressed = true;
        }
        if (!KeyboardEvent.getKeys()[GLFW_KEY_SPACE] && spacePressed) {
            spacePressed = false;
        }
        camera.getPosition().y -= verticalSpeed;
    }

    public Runnable ray(Map<Chunk, Mesh> chunkMeshMap, Matrix4f viewMatrix) {
        return () -> {
            viewDirection = viewMatrix.positiveZ(viewDirection).negate();
            IRay.createRayToBlock(chunkMeshMap, this);
        };
    }

    public void blockAddEvent(Map<Chunk, Mesh> chunkMeshMap, Block minDistBlock, Chunk chunk) {
        BlockEvent.blockAddEvent(chunkMeshMap, this, minDistBlock, chunk);
    }

    public void blockRemoveEvent(Map<Chunk, Mesh> chunkMeshMap, Block minDistBlock, Chunk chunk) {
        BlockEvent.blockRemoveEvent(chunkMeshMap, this, minDistBlock, chunk);
    }

    public void collisionDetection(Map<Chunk, Mesh> chunkMeshMap) {
        ICollision.collisionAABB(chunkMeshMap, this);
    }

    public void setRemovedBlock(Block removedBlock) {
        this.removedBlock = removedBlock;
    }

    public void setAddedBlock(Block addedBlock) {
        this.addedBlock = addedBlock;
    }

    public Block getRemovedBlock() {
        return removedBlock;
    }

    public Block getAddedBlock() {
        return addedBlock;
    }
}
