package dev.simurgh.core.ios;

import dev.simurgh.app.Controller;
import org.robovm.apple.coregraphics.CGRect;
import org.robovm.apple.glkit.GLKView;
import org.robovm.apple.glkit.GLKViewController;
import org.robovm.apple.glkit.GLKViewControllerDelegate;
import org.robovm.apple.glkit.GLKViewDelegate;
import org.robovm.apple.opengles.EAGLContext;
import org.robovm.apple.opengles.EAGLRenderingAPI;


import static dev.simurgh.core.ios.IOSGLES30.*;
import static dev.simurgh.nanovg.NanoVG.*;
import static dev.simurgh.nanovg.NanoVGGLES3.*;

public class SimurghViewController extends GLKViewController implements GLKViewDelegate, GLKViewControllerDelegate {

    private final Controller controller;
    private final int width, height;


    public SimurghViewController(Controller controller) {
        this.controller = controller;
        GLKView v = (GLKView) this.getView();
        EAGLContext context = new EAGLContext(EAGLRenderingAPI.OpenGLES3);
        v.setContext(context);
        EAGLContext.setCurrentContext(context);
        v.setDelegate(this);
        setDelegate(this);
        setPreferredFramesPerSecond(60);
        controller.vg = nvgCreate(NVG_ANTIALIAS);
        width = (int) this.getView().getFrame().getWidth();
        height = (int) this.getView().getFrame().getHeight();
    }

    @Override
    public void draw(GLKView glkView, CGRect cgRect) {
        glClearColor(1, 1, 1, 1);
        glClear(0x00004000);

        nvgBeginFrame(controller.vg, width, height, (float) width / height);
        controller.draw();
        nvgEndFrame(controller.vg);

    }

    @Override
    public void update(GLKViewController glkViewController) {

    }

    @Override
    public void willPause(GLKViewController glkViewController, boolean b) {

    }
}
