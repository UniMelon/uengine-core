package uengine.core.render;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;
import uengine.common.model.RawModel;
import uengine.common.model.Texture;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL46.*;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;
import static uengine.common.buffer.BufferManager.putData;

public class Loader { // загрузка моделей в память

    private List<Integer> vaos = new ArrayList<>();
    private List<Integer> vbos = new ArrayList<>();
    private List<Integer> textures = new ArrayList<>();

    // вернуть инф. в виде необработанной модели
    public RawModel loadToVao(float[] positions, float[] textureCoords, int[] indices) {
        int vaoId = createVao();
        bindIndicesBuffer(indices);
        storeDataToAttributeList(0, 3, positions);
        storeDataToAttributeList(1, 2, textureCoords);
        unbindVao();

        return new RawModel(vaoId, indices.length);
    }

    public int loadTexture(String fileName) {
        Texture texture = new Texture();

        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);
        IntBuffer ch = BufferUtils.createIntBuffer(1);

        try {
            ByteBuffer byteBuffer = stbi_load(fileName, w, h, ch, 4);

            texture.setData(byteBuffer);
            if (texture.getData() == null)
                throw new Exception("Image file " + fileName + " not loaded! " + STBImage.stbi_failure_reason());

            // после того как текстура прочиталась, присваеваем значения
            texture.setWidth(w.get());
            texture.setHeight(h.get());
            texture.setChannels(ch.get());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        switch (texture.getChannels()) {
            case 3:
                texture.setFormat(GL_RGB);
                texture.setInternalFormat(GL_RGB8);
                break;
            case 4:
                texture.setFormat(GL_RGBA);
                texture.setInternalFormat(GL_RGBA8);
                break;
        }

        texture.setId(glGenTextures());
        textures.add(texture.getId());
        glBindTexture(GL_TEXTURE_2D, texture.getId());
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        glTexImage2D(GL_TEXTURE_2D, 0, texture.getInternalFormat(), texture.getWidth(), texture.getHeight(), 0, texture.getFormat(), GL_UNSIGNED_BYTE, texture.getData());
        // хранит данные о текстуре
        //        glTextureStorage2D(texture.getId(), 1, texture.getInternalFormat(), texture.getWidth(), texture.getHeight());
        glGenerateMipmap(GL_TEXTURE_2D);

        // параметры для изображения
//        glTextureSubImage2D(texture.getId(), 0, 0, 0, texture.getWidth(), texture.getHeight(),
//                texture.getFormat(), GL_UNSIGNED_BYTE, texture.getData());

        // поведение текстуры, фильтрация
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);


        glBindTexture(GL_TEXTURE_2D, texture.getId());

        if (texture.getData() != null)
            stbi_image_free(texture.getData());

        return texture.getId();
    }

    private int createVao() {
        int vaoId = glGenVertexArrays(); // создать пустой VAO
        vaos.add(vaoId);
        glBindVertexArray(vaoId); // активировать для использования

        return vaoId;
    }

    private void storeDataToAttributeList(int attrNumber, int coordSize, float[] data) {
        int vboId = glGenBuffers(); // буффер для хранения данных
        vbos.add(vboId);

        glBindBuffer(GL_ARRAY_BUFFER, vboId); // когда он связан, в нем можно хранить данные
        glBufferData(GL_ARRAY_BUFFER, putData(data), GL_STATIC_DRAW);
        glVertexAttribPointer(
                attrNumber,
                coordSize,
                GL_FLOAT,
                false,
                0, // расстояние между вершинами (если нету никаких данных между ними - 0)
                0); // cмещенение с начала данных - 0
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public void cleanUp() {
        for (int vao: vaos) {
            glDeleteVertexArrays(vao);
        }
        for (int vbo: vbos) {
            glDeleteBuffers(vbo);
        }
        for (int texture: textures) {
            glDeleteTextures(texture);
        }
    }

    private void unbindVao() {
        glBindVertexArray(0); // отвязать текущий связанный VAO
        glBindVertexArray(1); // отвязать текущий связанный VAO
    }

    private void bindIndicesBuffer(int[] indices) {
        int iboId = glGenBuffers();
        vbos.add(iboId);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, putData(indices), GL_STATIC_DRAW);
    }

}
