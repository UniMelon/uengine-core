package uengine.common.model;

public class TexturedModel {

    private RawModel rawModel;
    private Texture texture;

    public TexturedModel(RawModel rawModel, Texture texture) {
        this.rawModel = rawModel;
        this.texture = texture;
    }

    public void setRawModel(RawModel rawModel) {
        this.rawModel = rawModel;
    }

    public void setTexModel(Texture texture) {
        this.texture = texture;
    }

    public RawModel getRawModel() {
        return rawModel;
    }

    public Texture getTexModel() {
        return texture;
    }
}
