package uengine.common.shader;

public class StaticShader extends AbstractShader{

    private static final String VERTEX_FILE = "src/main/resources/glsl/vertexShader.glsl";
    private static final String FRAGMENT_FILE = "src/main/resources/glsl/fragmentShader.glsl";

    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "texCoords");
    }
}
