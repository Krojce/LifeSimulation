package main;

import camera.Camera;
import camera.Target;
import input.Input;
import loader.Loader;
import manager.Display;
import manager.RenderManager;
import object.Object;
import terrain.Terrain;
import toolbox.Timer;

public class mainGameLoop {

  private static final int TARGET_UPS = 30;

  public static void main(String[] args) {

    float delta;
    float accumulator = 0f;
    float interval = 1f / TARGET_UPS;
    float alpha = 0;

    Display display = new Display();
    display.createDisplay();

    Timer timer = new Timer();
    timer.init();

    Target target = new Target();
    Camera camera = new Camera(target);
    Input input = new Input(camera);

    Loader loader = new Loader();

    RenderManager renderManager = new RenderManager();

    Terrain terrain = new Terrain(loader);
    Object object = new Object(loader);

    while (!display.shouldClose()) {

      delta = timer.getDelta();
      accumulator += delta;

      input.handleInput();

      while (accumulator >= interval) {
        camera.update();
        timer.updateUPS();
        accumulator -= interval;
      }

      renderManager.processTerrain(terrain);
      renderManager.processObject(object);
      renderManager.render(camera);
      timer.updateFPS();
      timer.update();

      /* Update window to show the new screen */
      input.checkInput();
      display.updateDisplay();
    }
    renderManager.cleanUp();
  }
}
