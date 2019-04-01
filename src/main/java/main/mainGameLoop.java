package main;

import camera.Camera;
import camera.Target;
import loader.Loader;
import manager.DisplayManager;
import manager.RenderManager;
import org.lwjgl.opengl.Display;
import terrain.Terrain;
import toolbox.MyMouse;

import java.lang.reflect.Field;

public class mainGameLoop {

  private static void setup(String pathToAdd) {
    Field usrPathsField;
    try {
      usrPathsField = ClassLoader.class.getDeclaredField("usr_paths");
      usrPathsField.setAccessible(true);

      String[] paths = (String[]) usrPathsField.get(null);

      for (String path : paths) if (path.equals(pathToAdd)) return;

      String[] newPaths = paths.clone();
      newPaths[newPaths.length - 1] = pathToAdd;
      usrPathsField.set(null, newPaths);
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {

    setup("src/main/resources/natives/" + determineOS());

    DisplayManager.createDisplay();
    Loader loader = new Loader();

    RenderManager renderer = new RenderManager();

    Terrain terrain = new Terrain(loader);

    Camera camera = new Camera(new Target());

    MyMouse mouse = MyMouse.getActiveMouse();

    while (!Display.isCloseRequested()) {
      camera.move();
      mouse.update();
      renderer.processTerrain(terrain);
      renderer.render(camera);
      DisplayManager.updateDisplay();
    }
    renderer.cleanUp();
    loader.cleanUp();
    DisplayManager.closeDisplay();
  }

  private static String determineOS() {
    String operationSystemName = System.getProperty("os.name");
    if (operationSystemName.startsWith("Windows")){
      return "windows";
    }else if (operationSystemName.startsWith("Mac")){
      return "macosx";
    }else {
      return null;
    }
  }
}
