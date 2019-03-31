package main;

import camera.Camera;
import camera.Target;
import loader.Loader;
import manager.DisplayManager;
import manager.RenderManager;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import terrain.Terrain;
import toolbox.Color;
import toolbox.Light;
import toolbox.MyMouse;

import java.lang.reflect.Field;
import java.util.Arrays;

public class mainGameLoop {

    private static void setup(String pathToAdd){
        Field usrPathsField = null;
        try {
            usrPathsField = ClassLoader.class.getDeclaredField("usr_paths");
            usrPathsField.setAccessible(true);

            String[] paths = (String[]) usrPathsField.get(null);

            for (String path : paths)
                if (path.equals(pathToAdd))
                    return;

            String[] newPaths = paths.clone();
            newPaths[newPaths.length - 1] = pathToAdd;
            usrPathsField.set(null, newPaths);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        setup("src/main/resources/natives/macosx");

        DisplayManager.createDisplay();
        Loader loader = new Loader();

        RenderManager renderer = new RenderManager();

        Light light = new Light(new Vector3f(0.3f, 1000f, 0.5f), new Color(1f, 0.8f, 0.8f), new Vector2f(0.9f, 0.2f));
        Terrain terrain = new Terrain(loader);

        Camera camera = new Camera(new Target());

        MyMouse mouse = MyMouse.getActiveMouse();

        while(!Display.isCloseRequested()){
            camera.move();
            mouse.update();
            renderer.processTerrain(terrain);
            renderer.render(light, camera);
            DisplayManager.updateDisplay();
        }
        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
