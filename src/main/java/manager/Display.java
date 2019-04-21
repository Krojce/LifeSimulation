package manager;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

public class Display {
  private final static int WIDTH = 1024;
  private final static int HEIGHT = 768;
  private final static String TITLE = "Life Simulation";

  private static long window;

  public void createDisplay() {
    if(!glfwInit()){
      System.exit(-1);
    }

    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
    glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
    glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);

    window = glfwCreateWindow(WIDTH, HEIGHT, TITLE, 0, 0);

    if (window == 0){
      System.exit(-1);
    }

    GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

    if (videoMode == null){
      System.exit(-1);
    }

    glfwSetWindowPos(window, (videoMode.width() - WIDTH) / 2, (videoMode.height() - HEIGHT) / 2);

    glfwMakeContextCurrent(window);
    glfwSwapInterval(1);
    glfwShowWindow(window);
    GL.createCapabilities();
  }

  public void updateDisplay() {
    glfwPollEvents();
    glfwSwapBuffers(window);
  }

  public void clearBuffers() {
    glfwSwapBuffers(window);
  }

  public boolean shouldClose() {
    return glfwWindowShouldClose(window);
  }

  public static int getWIDTH() {
    return WIDTH;
  }

  public static int getHEIGHT() {
    return HEIGHT;
  }

  public static String getTITLE() {
    return TITLE;
  }

  public static long getWindow() {
    return window;
  }
}
