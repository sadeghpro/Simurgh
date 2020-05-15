package dev.simurgh.example.basic;

import dev.simurgh.core.desktop.SimurghOpenGL;
import dev.simurgh.example.basic.core.ControllerMain;

public class Main {

    public static void main(String[] args) {
        new SimurghOpenGL(new ControllerMain(), 640, 480, "simurgh-basic");
    }
}
