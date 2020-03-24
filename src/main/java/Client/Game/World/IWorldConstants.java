package Client.Game.World;

public interface IWorldConstants {
    int WORLD_CHUNK_SIZE = 64;
    int TREE_SIZE = 2;  //for every chunk
    float gravity = 0.01f;
    float normalForce = 0.2f;
    float VIEW_DISTANCE = 64;
    float CHUNK_LOAD_DISTANCE = 4; //if less then more load distance bcs it depends on player pos
    float FRUSTUM_CULL_RADIUS = 32;
}
