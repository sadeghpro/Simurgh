package dev.simurgh.core.android;

import android.content.Context;
import android.opengl.GLSurfaceView;
import dev.simurgh.app.Controller;

public class SimurghOpenGl extends GLSurfaceView {

    static {
        System.loadLibrary("nanovg");
    }

    public SimurghOpenGl(Context context) {
        super(context);
    }

    public void init(Controller controller) {
        setEGLContextClientVersion(2);
        super.setEGLConfigChooser(8,8,8,8,16,0);
        setRenderer(new SimurghRenderer(controller));
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//TODO
    }

    @Override
    public void addOnUnhandledKeyEventListener(OnUnhandledKeyEventListener listener) {
        super.addOnUnhandledKeyEventListener(listener);
    }
}
