package uengine.common.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL46.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL46.glBindTexture;

@Getter
@Setter
@NoArgsConstructor
public class Texture {

    private int id;
    private int width, height;
    private int channels;

    private int format, internalFormat;

    private ByteBuffer data;
    private String resource;

    public Texture(int id) {
        this.id = id;
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, this.id);
    }

    public void unBind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

}
