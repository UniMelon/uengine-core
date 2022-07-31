package uengine.common.shader;

import org.lwjgl.opengl.GL46;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.lwjgl.opengl.GL20C.glDeleteShader;
import static org.lwjgl.opengl.GL46.*;

public abstract class AbstractShader {
    private final int programId;
    private final int vertexShaderId;
    private final int fragmentShaderId;

    public AbstractShader(String vertexFile, String fragmentFile) {
        vertexShaderId = loadShader(vertexFile, GL46.GL_VERTEX_SHADER);
        fragmentShaderId = loadShader(fragmentFile, GL46.GL_FRAGMENT_SHADER);
        programId = glCreateProgram();

        glAttachShader(programId, vertexShaderId); // прикрепить
        glAttachShader(programId, fragmentShaderId);
        bindAttributes();
        glLinkProgram(programId); // связывает шейдеры вместе
        glValidateProgram(programId);
    }

    public void start() {
        glUseProgram(programId);
    }

    public void stop() {
        glUseProgram(0);
    }

    public void cleanUp() {
        stop();
        glDetachShader(programId, vertexShaderId);
        glDetachShader(programId, fragmentShaderId);
        glDeleteShader(vertexShaderId);
        glDeleteShader(fragmentShaderId);
        glDeleteProgram(programId);
    }

    protected abstract void bindAttributes();

    protected void bindAttribute(int attribute, String variableName) {
        glBindAttribLocation(programId, attribute, variableName);
    }

    private static int loadShader(String file, int type) {
        StringBuilder shaderSource = new StringBuilder();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while((line = reader.readLine())!=null){
                shaderSource.append(line).append("\n");
            }
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
            System.exit(-1);
        }

        int shaderId = GL46.glCreateShader(type);
        GL46.glShaderSource(shaderId, shaderSource);
        GL46.glCompileShader(shaderId);

        if (GL46.glGetShaderi(shaderId, GL46.GL_COMPILE_STATUS) == GL46.GL_FALSE) {
            System.err.println(GL46.glGetShaderInfoLog(shaderId, 500));
            System.err.println("Could not compile shader: "+ file);
            System.exit(-1);
        }

        return shaderId;
    }
}
