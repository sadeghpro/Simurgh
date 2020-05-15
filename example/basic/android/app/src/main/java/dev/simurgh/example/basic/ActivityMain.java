package dev.simurgh.example.basic;

import android.app.Activity;
import android.os.Bundle;
import dev.simurgh.core.android.SimurghOpenGl;
import dev.simurgh.example.basic.core.ControllerMain;

public class ActivityMain extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SimurghOpenGl simurghOpenGl = new SimurghOpenGl(this);
        simurghOpenGl.init(new ControllerMain());
        setContentView(simurghOpenGl);
    }

}
