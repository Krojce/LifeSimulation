package main;

import camera.Camera;
import camera.DirectionalLight;
import camera.Target;
import entity.template.BaseEntity;
import factory.EntityFactory;
import gui.ButtonPanel;
import loader.Loader;
import manager.DisplayManager;
import manager.EntityManager;
import manager.RenderManager;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import render.GuiRenderer;
import terrain.Terrain;
import toolbox.Color;
import toolbox.Timer;
import toolbox.input.MyMouse;
import toolbox.input.Raycast;

import java.lang.reflect.Field;
import java.util.Random;

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

        RenderManager renderer = new RenderManager(loader);
        EntityFactory entityFactory = new EntityFactory(loader);
        EntityManager entityManager = new EntityManager();

        Terrain terrain = new Terrain(loader);
        Camera camera = new Camera(new Target(new Vector3f(Terrain.getSIZE() / 2, 50, Terrain.getSIZE() / 2)));
        MyMouse mouse = MyMouse.getActiveMouse();

        Random random = new Random();

        for (int i = 0; i < 200; i++) {
            float randomX = random.nextFloat() * Terrain.getSIZE();
            float randomZ = random.nextFloat() * Terrain.getSIZE();
            entityManager.addEntity(entityFactory.createEntity(new Vector3f(randomX, 0, randomZ), "tree"));
        }

        Raycast raycast = new Raycast(camera, renderer.getProjectionMatrix(), terrain);

        GuiRenderer guiRenderer = new GuiRenderer(loader);
        ButtonPanel buttonPanel = new ButtonPanel(mouse, loader);

        Timer timer = new Timer();

        while (!Display.isCloseRequested()) {
            System.out.println(Timer.getCurrentTime());
            for (BaseEntity entity : entityManager.getEntities()) {
                renderer.processEntity(entity);
            }

            buttonPanel.update();

            camera.move();
            mouse.update();
            raycast.update();

            if (mouse.isLeftClick()) {
                Vector3f position = raycast.getCurrentTerrainPoint();
                if (position != null) {
                    entityManager.addEntity(entityFactory.createEntity(position, buttonPanel.getActiveEntity()));
                }
            }

            renderer.processTerrain(terrain);
            renderer.render(camera, sun);
            guiRenderer.render(buttonPanel);
            DisplayManager.updateDisplay();
            timer.update();
        }

        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

    private static String determineOS() {
        String operationSystemName = System.getProperty("os.name");
        if (operationSystemName.startsWith("Windows")) {
            return "windows";
        } else if (operationSystemName.startsWith("Mac")) {
            return "macosx";
        } else {
            return null;
        }
    }
}
