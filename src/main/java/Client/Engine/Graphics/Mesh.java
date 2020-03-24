package Client.Engine.Graphics;

import Client.Utils.BufferUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL45.glCreateBuffers;
import static org.lwjgl.opengl.GL45.glCreateVertexArrays;

public class Mesh {

    private float[] vertices, tcs;
    private int[] indices;

    private static FloatBuffer floatBuffer;
    private static IntBuffer intBuffer;

    private int vbo, ibo, tbo, vao;

    public Mesh(float[] vertices, int[] indices, float[] tcs) {
        this.vertices = vertices;
        this.indices = indices;
        this.tcs = tcs;
    }

    public boolean empty() {
        return vbo == 0 && ibo == 0 && tbo == 0;
    }

    public void load(){
        vao = glCreateVertexArrays();
        glBindVertexArray(vao);

        floatBuffer = BufferUtil.createFloatBuffer(vertices);
        vbo = glCreateBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, floatBuffer, GL_STATIC_DRAW);
        glEnableVertexAttribArray(Shader.VERTICES);
        glVertexAttribPointer(Shader.VERTICES, 3, GL_FLOAT, false, Float.BYTES*4, 0);

        glEnableVertexAttribArray(Shader.BRIGHTNESS);
        glVertexAttribPointer(Shader.BRIGHTNESS, 1, GL_FLOAT, false, Float.BYTES*4, Float.BYTES*3);
        floatBuffer.clear();

        intBuffer = BufferUtil.createIntBuffer(indices);
        ibo = glCreateBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, intBuffer, GL_STATIC_DRAW);
        intBuffer.clear();

        floatBuffer = BufferUtil.createFloatBuffer(tcs);
        tbo = glCreateBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, tbo);
        glBufferData(GL_ARRAY_BUFFER, floatBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(Shader.TCS, 2, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(Shader.TCS);

        floatBuffer.clear();
        intBuffer.clear();
    }

    public void loadWithoutData(){
        vao = glCreateVertexArrays();
        glBindVertexArray(vao);

        vbo = glCreateBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices.length * Float.BYTES, GL_DYNAMIC_DRAW);
        glVertexAttribPointer(Shader.VERTICES, 3, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(Shader.VERTICES);

        intBuffer = BufferUtil.createIntBuffer(indices);
        ibo = glCreateBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, intBuffer, GL_STATIC_DRAW);
        intBuffer.clear();

        floatBuffer = BufferUtil.createFloatBuffer(tcs);
        tbo = glCreateBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, tbo);
        glBufferData(GL_ARRAY_BUFFER, floatBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(Shader.TCS, 2, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(Shader.TCS);

        floatBuffer.clear();
        intBuffer.clear();
    }

    private void bind() {
        glBindVertexArray(vao);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
    }

    private void draw() {
        glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
    }

    private void subData(FloatBuffer data) {
        glBufferSubData(GL_ARRAY_BUFFER, 0, data);
    }

    private void unbind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    public void delete() {
        glDeleteBuffers(vbo);
        glDeleteBuffers(ibo);
        glDeleteBuffers(tbo);
    }

    public void render() {
        bind();
        draw();
        unbind();
    }

    public void renderSubData(FloatBuffer data){
        bind();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        subData(data);
        draw();
        unbind();
    }
}
