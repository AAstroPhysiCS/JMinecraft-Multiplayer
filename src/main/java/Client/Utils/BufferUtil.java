package Client.Utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class BufferUtil {
    public static FloatBuffer createFloatBuffer(float[] data){
        return ByteBuffer.allocateDirect(data.length << 2).order(ByteOrder.nativeOrder()).asFloatBuffer().put(data).flip();
    }

    public static IntBuffer createIntBuffer(int[] data){
        return ByteBuffer.allocateDirect(data.length << 2).order(ByteOrder.nativeOrder()).asIntBuffer().put(data).flip();
    }
}
