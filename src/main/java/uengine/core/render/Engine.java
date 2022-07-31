package uengine.core.render;

import uengine.common.model.RawModel;
import uengine.common.model.Texture;
import uengine.common.model.TexturedModel;
import uengine.common.shader.StaticShader;
import uengine.window.WindowManager;

public class Engine {

    public static final int WIDHT = 1280;
    public static final int HEIGHT = 720;
    public static final String TITLE = "uEngine 0.0.1 pre-alpha";
    private WindowManager windowManager;

    public void run() {
        this.init();
    }

    private void init() {
        this.windowManager = new WindowManager(WIDHT, HEIGHT, TITLE);
        this.windowManager.create();
        this.update();
    }

    private void update() {
        Loader loader = new Loader();
        RenderFromVao renderFromVao = new RenderFromVao();
        StaticShader shader = new StaticShader();

//        float[] vertices = {
//                -0.5f, 0.5f, 0f,
//                -0.5f, -0.5f, 0f,
//                0.5f, -0.5f, 0f,
//                0.5f, -0.5f, 0f,
//                0.5f, 0.5f, 0f,
//                -0.5f, 0.5f, 0f
//        };

        float[] vertices = {
                -0.5f, 0.5f, 0f, //v0
                -0.5f, -0.5f, 0f, //v1
                0.5f, -0.5f, 0f, //v2
                0.5f, 0.5f, 0f, //v3
        };

        int[] indices = {
                0,1,3, //top left triangle (v0, v1, v3)
                3,1,2 //bottom right triangle (v3, v1, v2)
        };

        float[] textureCoords = {
                0,0, // v0
                0,1, // v1
                1,1, // v2
                1,0 // v3
        };

        RawModel model = loader.loadToVao(vertices, textureCoords, indices);
        int i = loader.loadTexture("src/main/resources/textures/panda.png");
        Texture texture = new Texture(i);
        TexturedModel texturedModel = new TexturedModel(model, texture);

        while(!this.windowManager.isCloseRequest()) {
            renderFromVao.prepare();

            texture.bind();
            shader.start();
            renderFromVao.render(texturedModel);
            shader.stop();
            texture.unBind();
            this.windowManager.update();
        }

        this.windowManager.cleanUp();
        loader.cleanUp();
        shader.cleanUp();
    }
}
