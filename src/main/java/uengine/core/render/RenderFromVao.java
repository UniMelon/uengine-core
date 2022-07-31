package uengine.core.render;

import uengine.common.model.RawModel;
import uengine.common.model.TexturedModel;

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

    public void render(TexturedModel texturedModel) {
        RawModel model = texturedModel.getRawModel();

        glBindVertexArray(model.getVaoId());
        glEnableVertexAttribArray(0);// активировать список аттрибутов, в котором хранятся данные
        glEnableVertexAttribArray(1);
//        glDrawArrays(GL_TRIANGLES, 0, texturedModel.getVertexCount()); // указать что визуализировать
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, texturedModel.getTexModel().getId());
        glDrawElements(GL_TRIANGLES, model.getVertexCount(), GL_UNSIGNED_INT, 0);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);
    }
}
