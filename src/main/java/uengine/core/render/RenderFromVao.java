package uengine.core.render;

import org.joml.Matrix4f;
import uengine.common.model.Entity;
import uengine.common.model.RawModel;
import uengine.common.model.TexturedModel;
import uengine.common.shader.StaticShader;
import uengine.common.util.Maths;

import static org.lwjgl.opengl.GL46.*;

public class RenderFromVao {

    public void prepare() { // вызывается один раз в каждом кадре, подготовка OpenGL к визуализации
//        glClearColor(0.5f, 0.2f, 0.4f, 0);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
    }

//    public void render(RawModel model) {
//        glBindVertexArray(model.getVaoId());
//        glEnableVertexAttribArray(0);// активировать список аттрибутов, в котором хранятся данные
////        glDrawArrays(GL_TRIANGLES, 0, model.getVertexCount()); // указать что визуализировать
//        glDrawElements(GL_TRIANGLES, model.getVertexCount(), GL_UNSIGNED_INT, 0);
//        glDisableVertexAttribArray(0);
//        glBindVertexArray(0);
//    }

//    public void render(TexturedModel texturedModel) {
//        RawModel model = texturedModel.getRawModel();
//
//        glBindVertexArray(model.getVaoId());
//        glEnableVertexAttribArray(0);// активировать список аттрибутов, в котором хранятся данные
//        glEnableVertexAttribArray(1);
////        glDrawArrays(GL_TRIANGLES, 0, texturedModel.getVertexCount()); // указать что визуализировать
//        glActiveTexture(GL_TEXTURE0);
//        glBindTexture(GL_TEXTURE_2D, texturedModel.getTexture().getId());
//        glDrawElements(GL_TRIANGLES, model.getVertexCount(), GL_UNSIGNED_INT, 0);
//        glDisableVertexAttribArray(0);
//        glDisableVertexAttribArray(1);
//        glBindVertexArray(0);
//    }

    public void render(Entity entity, StaticShader shader) {
        TexturedModel model = entity.getModel();
        RawModel rawModel = model.getRawModel();

        glBindVertexArray(rawModel.getVaoId());
        glEnableVertexAttribArray(0);// активировать список аттрибутов, в котором хранятся данные
        glEnableVertexAttribArray(1);

        Matrix4f matrix = Maths.createTransformationMatrix(
                entity.getPosition(), entity.getRx(), entity.getRy(), entity.getRz(), entity.getScale());

        shader.setMatrix4f(matrix);

        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, model.getTexture().getId());
        glDrawElements(GL_TRIANGLES, rawModel.getVertexCount(), GL_UNSIGNED_INT, 0);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);
    }
}
