package factory;

import entity.*;
import entity.template.BaseEntity;
import loader.Loader;
import loader.OBJLoader;
import model.RawModel;
import model.TexturedModel;
import org.lwjgl.util.vector.Vector3f;
import textures.ModelTexture;

import java.util.HashMap;

public class EntityFactory {

    private HashMap<String, TexturedModel> texturedModels = new HashMap<String, TexturedModel>();
    private Loader loader;

    public EntityFactory(Loader loader) {
        this.loader = loader;
        String[] names = {"tree", "deer", "bear", "rabbit", "boar"};
        for (String name : names) {
            texturedModels.put(name, createTexturedModel(name));
        }
    }

    public BaseEntity createEntity(Vector3f position, String name) {
        if (name.equals("tree")) {
            return createTree(position);
        }

        if (name.equals("bear")) {
            return createBear(position);
        }

        if (name.equals("deer")) {
            return createDeer(position);
        }

        if (name.equals("rabbit")) {
            return createRabbit(position);
        }

        if (name.equals("boar")) {
            return createBoar(position);
        }

        return null;
    }

    private Bear createBear(Vector3f position) {
        return new Bear(texturedModels.get("bear"), position, new Vector3f(0, 0, 0), 2f);
    }

    private Tree createTree(Vector3f position) {
        return new Tree(texturedModels.get("tree"), position, new Vector3f(0, 0, 0), 2f);
    }

    private Deer createDeer(Vector3f position) {
        return new Deer(texturedModels.get("deer"), position, new Vector3f(0, 0, 0), 2f);
    }

    private Rabbit createRabbit(Vector3f position) {
        return new Rabbit(texturedModels.get("rabbit"), position, new Vector3f(0, 0, 0), 2f);
    }

    private Boar createBoar(Vector3f position) {
        return new Boar(texturedModels.get("boar"), position, new Vector3f(0, 0, 0), 2f);
    }

    private TexturedModel createTexturedModel(String name) {
        RawModel rawModel = OBJLoader.loadObjModel(name, loader);
        ModelTexture texture = new ModelTexture(loader.loadTexture(name + "-skin"));
        return new TexturedModel(rawModel, texture);
    }
}
