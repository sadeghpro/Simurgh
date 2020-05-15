package dev.simurgh.core.desktop;

import dev.simurgh.app.Controller;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.Platform;

import java.io.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static dev.simurgh.nanovg.NanoVG.*;
import static dev.simurgh.nanovg.NanoVG.nvgEndFrame;
import static dev.simurgh.nanovg.NanoVGGL3.NVG_ANTIALIAS;
import static dev.simurgh.nanovg.NanoVGGL3.nvgCreate;
import static java.lang.Math.max;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class SimurghOpenGL {

    static {
        try {
            System.loadLibrary("nanovg");
        } catch (UnsatisfiedLinkError ignore) {
            try {
                String OS = System.getProperty("os.name").toLowerCase();
                String addr = "";
                if (OS.startsWith("windows")) {
                    if (System.getProperty("os.arch").contains("64")) { //TODO 'os.arch' give jre architecture not os
                        addr = "/libs/win64/nanovg.dll";
                    } else {
                        addr = "/libs/win32/nanovg.dll";
                    }
                } else if (OS.startsWith("mac")) {
                    addr = "/libs/mac/libnanovg.dylib";
                } else if (OS.startsWith("linux")) {
                    if (System.getProperty("os.arch").contains("64")) { //TODO 'os.arch' give jre architecture not os
                        addr = "/libs/linux64/libnanovg.so";
                    } else {
                        addr = "/libs/linux32/libnanovg.so";
                    }
                }
                InputStream is = SimurghOpenGL.class.getResourceAsStream(addr);
                File file = new File(addr.substring(addr.lastIndexOf("/") + 1));
                OutputStream os = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) != -1) {
                    os.write(buffer, 0, length);
                }
                is.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.loadLibrary("nanovg");
        }
    }

    public SimurghOpenGL(Controller controller, int width, int height, String title) {
        if (!glfwInit()) {
            throw new IllegalStateException("Failed to initials GLFW");
        }

        if (Platform.get() == Platform.MACOSX) {
            glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
            glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
            glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        }
        glfwWindowHint(GLFW_SCALE_TO_MONITOR, GLFW_TRUE);

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        long window = glfwCreateWindow(width, height, title, 0, 0);
        if (window == 0) {
            throw new IllegalStateException("Failed to create window");
        }

        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        if (vidMode != null) {
            glfwSetWindowPos(window, (vidMode.width() - width) / 2, (vidMode.height() - height) / 2);
        }
        glfwShowWindow(window);
        glfwMakeContextCurrent(window);
        GL.createCapabilities();

        controller.vg = nvgCreate(NVG_ANTIALIAS);

        if (controller.vg == NULL) {
            throw new RuntimeException("Could not init nanovg.");
        }

        float contentScaleX, contentScaleY;
        try (MemoryStack stack = stackPush()) {
            IntBuffer fw = stack.mallocInt(1);
            IntBuffer fh = stack.mallocInt(1);
            FloatBuffer sx = stack.mallocFloat(1);
            FloatBuffer sy = stack.mallocFloat(1);

            glfwGetFramebufferSize(window, fw, fh);

            glfwGetWindowContentScale(window, sx, sy);
            contentScaleX = sx.get(0);
            contentScaleY = sy.get(0);
        }
        glfwSwapInterval(1);

        while (!glfwWindowShouldClose(window)) {
            glfwPollEvents();

            glClearColor(1, 1, 1, 1);
            glClear(GL_COLOR_BUFFER_BIT);

            nvgBeginFrame(controller.vg, width, height, max(contentScaleX, contentScaleY));

            controller.draw();

            nvgEndFrame(controller.vg);

            glfwSwapBuffers(window);
        }

        glfwTerminate();
    }
}
