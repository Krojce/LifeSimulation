package main;

import camera.Camera;
import camera.DirectionalLight;
import camera.Target;
import entity.BaseEntity;
import gui.ButtonPanel;
import loader.Loader;
import loader.OBJLoader;
import manager.DisplayManager;
import manager.RenderManager;
import model.RawModel;
import model.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import render.GuiRenderer;
import terrain.Terrain;
import textures.ModelTexture;
import toolbox.Color;
import toolbox.input.MyMouse;
import toolbox.input.Raycast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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

    DirectionalLight sun = new DirectionalLight(
            new Vector3f(-0.2f, -1.0f, -0.3f),
            new Vector3f(0.2f, 0.2f, 0.2f),
            new Vector3f(0.5f, 0.5f, 0.5f),
            new Vector3f(1.0f, 1.0f, 1.0f),
            new Color(1.0f, 1.0f, 1.0f));

    RenderManager renderer = new RenderManager();

    Terrain terrain = new Terrain(loader);
    Camera camera = new Camera(new Target(new Vector3f(Terrain.getSIZE() / 2, 50, Terrain.getSIZE() / 2)));
    MyMouse mouse = MyMouse.getActiveMouse();

    RawModel deer = OBJLoader.loadObjModel("deer", loader);
    ModelTexture deerTexture = new ModelTexture(loader.loadTexture("deer-skin"));
    TexturedModel texturedDeer = new TexturedModel(deer, deerTexture);

    RawModel boar = OBJLoader.loadObjModel("boar", loader);
    ModelTexture boarTexture = new ModelTexture(loader.loadTexture("boar-skin"));
    TexturedModel texturedBoar = new TexturedModel(boar, boarTexture);

    RawModel bear = OBJLoader.loadObjModel("bear", loader);
    ModelTexture bearTexture = new ModelTexture(loader.loadTexture("bear-skin"));
    TexturedModel texturedBear = new TexturedModel(bear, bearTexture);

    RawModel rabbit = OBJLoader.loadObjModel("rabbit", loader);
    ModelTexture rabbitTexture = new ModelTexture(loader.loadTexture("rabbit-skin"));
    TexturedModel texturedRabbit = new TexturedModel(rabbit, rabbitTexture);

    List<TexturedModel> texturedModels = new ArrayList<TexturedModel>();
    texturedModels.add(texturedBear);
    texturedModels.add(texturedDeer);
    texturedModels.add(texturedBoar);
    texturedModels.add(texturedRabbit);

    List<BaseEntity> entities = new ArrayList<BaseEntity>();

    Raycast raycast = new Raycast(camera, renderer.getProjectionMatrix(), terrain);

    GuiRenderer guiRenderer = new GuiRenderer(loader);
    ButtonPanel buttonPanel = new ButtonPanel(mouse, loader);

    while (!Display.isCloseRequested()) {
      for (BaseEntity entity : entities) {
        renderer.processEntity(entity);
      }

      buttonPanel.isOnButton();

      camera.move();
      mouse.update();
      renderer.processTerrain(terrain);
      renderer.render(camera, sun);
      guiRenderer.render(buttonPanel);
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
