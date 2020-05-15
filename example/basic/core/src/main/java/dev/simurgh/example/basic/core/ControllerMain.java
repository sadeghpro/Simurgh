package dev.simurgh.example.basic.core;

import dev.simurgh.app.Controller;
import static dev.simurgh.nanovg.NanoVG.*;

public class ControllerMain extends Controller {

    @Override
    public void draw() {
        nvgBeginPath(vg);
        nvgRoundedRect(vg, 40, 40, 150, 150, 20);
        nvgFillColor(vg, .9f, .5f, .5f, 1);
        nvgFill(vg);
    }
}
