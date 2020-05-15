package dev.simurgh.core.android;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import dev.simurgh.app.Controller;
import dev.simurgh.nanovg.NanoVGGLES2;
import static dev.simurgh.nanovg.NanoVG.*;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class SimurghRenderer implements GLSurfaceView.Renderer {

    private int width, height;
    private final Controller controller;

    public SimurghRenderer(Controller controller){
        this.controller = controller;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        controller.vg = NanoVGGLES2.nvgCreate(NanoVGGLES2.NVG_ANTIALIAS);
        if (controller.vg == 0) {
            throw new RuntimeException("Could not init nanovg.");
        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClearColor(1, 1, 1, 1);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        nvgBeginFrame(controller.vg, width, height, (float) width/height);
        controller.draw();
        nvgEndFrame(controller.vg);
    }
}
