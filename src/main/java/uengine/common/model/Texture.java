package uengine.common.model;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL46;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL46.*;
import static org.lwjgl.stb.STBImage.*;
import static uengine.common.buffer.BufferManager.*;

public class Texture {

    private int id;
    private int width, height;
    private int channels;

    private int format, internalFormat;

    private ByteBuffer data;
    private String resource;

    public Texture() {
    }

    public Texture(int id) {
        this.id = id;
    }

//    public Texture(String resource) {
////        this.resource = resource;
////
////        IntBuffer w = BufferUtils.createIntBuffer(1);
////        IntBuffer h = BufferUtils.createIntBuffer(1);
////        IntBuffer ch = BufferUtils.createIntBuffer(1);
////
////        try {
////            data = stbi_load_from_memory(resourceToByteBuffer(resource), w, h, ch, 0);
////            // после того как текстура прочиталась, присваеваем значения
////            this.width = w.get(0);
////            this.height = h.get(0);
////            this.channels = ch.get(0);
////
////        } catch (IOException e) {
////            throw new RuntimeException(e);
////        }
////
////        switch (this.channels) {
////            case 3:
////                this.format = GL_RGB;
////                this.internalFormat = GL_RGB8;
////                break;
////            case 4:
////                this.format = GL_RGBA;
////                this.internalFormat = GL_RGBA8;
////                break;
////        }
////
////        this.id = glCreateTextures(GL_TEXTURE_2D);
////        glTextureStorage2D(id, 1, internalFormat, width, height); // хранит данные о текстуре
////
////        // поведение текстуры, фильтрация
////        glTextureParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
////        glTextureParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
////        glTextureParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
////        glTextureParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
////
////        // параметры для изображения
////        glTextureSubImage2D(id, 0, 0, 0, width, height, format, GL_UNSIGNED_BYTE, data);
////
////        if (data != null)
////            stbi_image_free(data);
//    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, this.id);
    }

    public void unBind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public int getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ByteBuffer getData() {
        return data;
    }

    public String getResource() {
        return resource;
    }

    public void setData(ByteBuffer data) {
        this.data = data;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setChannels(int channels) {
        this.channels = channels;
    }

    public int getChannels() {
        return channels;
    }

    public int getFormat() {
        return format;
    }

    public int getInternalFormat() {
        return internalFormat;
    }

    public void setFormat(int format) {
        this.format = format;
    }

    public void setInternalFormat(int internalFormat) {
        this.internalFormat = internalFormat;
    }
}
