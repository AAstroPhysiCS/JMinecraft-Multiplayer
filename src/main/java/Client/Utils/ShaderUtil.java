package Client.Utils;

import static org.lwjgl.opengl.GL20.*;

public class ShaderUtil {

    private static String vertex, fragment;

    private ShaderUtil(){}

    public static int load(String vertexPath, String fragmentPath){
        vertex = FileUtil.loadAsString(vertexPath);
        fragment = FileUtil.loadAsString(fragmentPath);
        return create();
    }

    private static int create(){
        int program = glCreateProgram();
        int vertexId = glCreateShader(GL_VERTEX_SHADER);
        int fragmentId = glCreateShader(GL_FRAGMENT_SHADER);

        glShaderSource(vertexId, vertex);
        glShaderSource(fragmentId, fragment);

        glCompileShader(vertexId);
        int statusFS = glGetShaderi(vertexId, GL_COMPILE_STATUS);
        if(statusFS != GL_TRUE){
            System.out.println("Fragment Shader crashed: " + glGetShaderInfoLog(vertexId));
        }

        glCompileShader(fragmentId);
        int statusVS = glGetShaderi(fragmentId, GL_COMPILE_STATUS);
        if(statusVS != GL_TRUE){
            System.out.println("Vertex Shader crashed: " + glGetShaderInfoLog(fragmentId));
        }

        glAttachShader(program, vertexId);
        glAttachShader(program, fragmentId);
        glLinkProgram(program);
        glValidateProgram(program);

        glDeleteShader(vertexId);
        glDeleteShader(fragmentId);

        return program;
    }
}
