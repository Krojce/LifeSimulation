package main;

import camera.Camera;
import camera.Target;
import factory.EntityFactory;
import font.FontType;
import font.GUIText;
import gui.ButtonPanel;
import lights.DirectionalLight;
import lights.Light;
import lights.PointLight;
import loader.Loader;
import manager.DisplayManager;
import manager.EntityManager;
import manager.RenderManager;
import manager.TextManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import render.GuiRenderer;
import resources.Rock;
import terrain.Terrain;
import toolbox.Color;
import toolbox.Timer;
import toolbox.input.MyMouse;
import toolbox.picking.EntityPicker;
import toolbox.picking.TerrainCollisionDetector;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

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
        Random random = new Random();
        EntityFactory entityFactory = new EntityFactory(loader);
        Terrain terrain = new Terrain(loader);
        List<Light> lights = new ArrayList<Light>();
        List<Rock> rocks = new ArrayList<Rock>();

        TextManager textManager = new TextManager(loader);
        FontType font = new FontType(loader.loadTexture("verdana"), new File("src/main/java/resources/verdana.fnt"));
        GUIText text = new GUIText("NATURE SIMULATION", 2f, font, new Vector2f(0f, 0.93f), 1f, true);
        text.setColor(1, 1, 1);

        DirectionalLight sun = new DirectionalLight(
                new Vector3f(0.2f, 0.2f, 0.2f),
                new Vector3f(0.01f, 0.01f, 0.01f),
                new Color(1.0f, 1.0f, 1.0f),
                new Vector3f(-0.2f, -1.0f, -0.3f)
        );

        for (int i = 0; i < 5; i++) {
            float randomX = random.nextFloat() * 200 + Terrain.getSIZE() / 2;
            float randomZ = random.nextFloat() * 200 + Terrain.getSIZE() / 2;

            float randomR = random.nextFloat();
            float randomG = random.nextFloat();
            float randomB = random.nextFloat();

            rocks.add(entityFactory.createRock(new Vector3f(randomX, Terrain.getHeight(randomX, randomZ), randomZ)));

            lights.add(new PointLight(
                    new Vector3f(0.2f, 0.2f, 0.2f),
                    new Vector3f(0.01f, 0.01f, 0.01f),
                    new Color(randomR, randomG, randomB),
                    new Vector3f(randomX, 1, randomZ),
                    new Vector3f(1, 0.1f, 0.01f)
            ));
        }

        Camera camera = new Camera(new Target(new Vector3f(Terrain.getSIZE() / 2, 50, Terrain.getSIZE() / 2)));
        RenderManager renderer = new RenderManager(loader, rocks);
        TerrainCollisionDetector terrainCollisionDetector = new TerrainCollisionDetector(camera, renderer.getProjectionMatrix());
        EntityPicker entityPicker = new EntityPicker(camera, renderer.getProjectionMatrix());
        EntityManager entityManager = new EntityManager();



        MyMouse mouse = MyMouse.getActiveMouse();

        for (int i = 0; i < 50; i++) {
            float randomX = random.nextFloat() * Terrain.getSIZE();
            float randomZ = random.nextFloat() * Terrain.getSIZE();
            entityManager.addEntity(entityFactory.createEntity(new Vector3f(randomX, 0, randomZ), "tree", entityPicker));
        }

        GuiRenderer guiRenderer = new GuiRenderer(loader);
        ButtonPanel buttonPanel = new ButtonPanel(mouse, loader);

        Timer timer = new Timer();

        while (!Display.isCloseRequested()) {

            if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) && Keyboard.isKeyDown(Keyboard.KEY_R)) {
                entityManager.clearWorld();
            }

            buttonPanel.update();

            camera.move();
            mouse.update();
            terrainCollisionDetector.update();
            sun.update();

            if (MyMouse.isLeftClick()) {
                Vector3f position = terrainCollisionDetector.getCurrentTerrainPoint();
                if (position != null) {
                    if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
                        entityManager.addEntity(entityFactory.createEntity(position, buttonPanel.getActiveEntity(), entityPicker));
                    } else {
                        entityManager.handlePicking(position);
                    }
                }
            }

            renderer.processTerrain(terrain);
            renderer.render(camera, lights, sun);
            guiRenderer.render(buttonPanel);
            textManager.render();
            DisplayManager.updateDisplay();
            timer.update();
        }

        textManager.cleanUp();
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
