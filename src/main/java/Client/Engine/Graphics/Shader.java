package Client.Engine.Graphics;

import Client.Engine.Screen.Screen;
import Client.Utils.ShaderUtil;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;

public class Shader {

    private final int program;
    public static final int VERTICES = 0;
    public static final int TCS = 1;
    public static final int BRIGHTNESS = 3;

    private final Map<String, ? super Number> uniformLocations = new HashMap<>();

    private Matrix4f projectionMatrix, modelMatrix, viewMatrix;

    private static final FloatBuffer buffer = BufferUtils.createFloatBuffer(16);

    public Shader(String vertexPath, String fragmentPath){
        program = ShaderUtil.load(vertexPath, fragmentPath);
    }

    public void enable() {
        glUseProgram(program);
    }

    public void setUniform1i(String name, int value) {
        if (!uniformLocations.containsKey(name)) {
            uniformLocations.put(name, glGetUniformLocation(program, name));
            return;
        }
        glUniform1i((Integer) uniformLocations.get(name), value);
    }

    public void setUniform1f(String name, float value) {
        if (!uniformLocations.containsKey(name)) {
            uniformLocations.put(name, glGetUniformLocation(program, name));
            return;
        }
        glUniform1f((Integer) uniformLocations.get(name), value);
    }

    public void setUniform4fv(Matrix4f m, int location, boolean transpose){
        m.get(buffer);
        glUniformMatrix4fv(location, transpose, buffer);
        buffer.clear();
    }

    public Matrix4f setProjectionMatrix(Vector3f rotation) {
        Matrix4f m = new Matrix4f();
        m.identity();
        m.perspective(68, (float) Screen.getWidth() / Screen.getHeight(), 0.03f, 1000f);
        m.rotateX(rotation.x);
        m.rotateY(rotation.y);
        m.rotateZ(rotation.z);
        return m;
    }

    public Matrix4f setModelMatrix(Vector3f translation) {
        Matrix4f m = new Matrix4f();
        m.identity();
        m.translate(-translation.x, -translation.y, -translation.z);
        return m;
    }

    public void setViewMatrix(Vector3f translation, Vector3f rotation) {
        projectionMatrix = setProjectionMatrix(rotation);
        modelMatrix = setModelMatrix(translation);
        viewMatrix = new Matrix4f();
        viewMatrix.set(projectionMatrix);
        viewMatrix.mul(modelMatrix);
        setUniform4fv(viewMatrix, 2, true);
    }

    public void disable() {
        glUseProgram(0);
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }
}
