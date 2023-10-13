package uengine.common.shader;

import org.joml.Matrix4f;

public class StaticShader extends AbstractShader{

    private static final String VERTEX_FILE = "src/main/resources/glsl/vertexShader.glsl";
    private static final String FRAGMENT_FILE = "src/main/resources/glsl/fragmentShader.glsl";

    private int location_mat4;

    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "texCoords");
    }

    @Override
    protected void findUniformLocations() {
        this.location_mat4 = super.getUniformLocation("transformationMatrix");
    }

    public void setMatrix4f(Matrix4f matrix) {
        super.setMaxrix4f(location_mat4, matrix);
    }
}
