package dev.simurgh.example.basic;

import android.app.Activity;
import android.os.Bundle;
import dev.simurgh.core.android.SimurghOpenGL;
import dev.simurgh.example.basic.core.ControllerMain;

public class ActivityMain extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SimurghOpenGL simurghOpenGl = new SimurghOpenGL(this);
        simurghOpenGl.init(new ControllerMain());
        setContentView(simurghOpenGl);
    }

}
