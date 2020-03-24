package Client.Game.Interaction;

import Client.Game.World.Chunk.Chunk;
import org.joml.FrustumIntersection;
import org.joml.Matrix4f;

import java.util.Set;

import static Client.Game.World.IWorldConstants.FRUSTUM_CULL_RADIUS;

public interface IFrustumCuller {

    static void filterFrustum(Set<Chunk> chunks, Matrix4f m) {
        FrustumIntersection frustum = new FrustumIntersection(m);
        for (Chunk chunk : chunks) {
            chunk.setInsideFrustum(false);
            if (frustum.testSphere(chunk.getPosition(), FRUSTUM_CULL_RADIUS)) {
                chunk.setInsideFrustum(true);
            }
        }
    }
}
