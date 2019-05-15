package factory;

import entity.*;
import entity.template.BaseEntity;
import loader.Loader;
import loader.OBJLoader;
import model.RawModel;
import model.TexturedModel;
import org.lwjgl.util.vector.Vector3f;
import textures.ModelTexture;
import toolbox.picking.EntityPicker;

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

    public BaseEntity createEntity(Vector3f position, String name, EntityPicker picker) {
        if (name.equals("tree")) {
            return createTree(position, picker);
        }

        if (name.equals("bear")) {
            return createBear(position, picker);
        }

        if (name.equals("deer")) {
            return createDeer(position, picker);
        }

        if (name.equals("rabbit")) {
            return createRabbit(position, picker);
        }

        if (name.equals("boar")) {
            return createBoar(position, picker);
        }

        return null;
    }

    private Bear createBear(Vector3f position, EntityPicker picker) {
        return new Bear(texturedModels.get("bear"), position, new Vector3f(0, 0, 0), 2.5f, picker);
    }

    private Tree createTree(Vector3f position, EntityPicker picker) {
        return new Tree(texturedModels.get("tree"), position, new Vector3f(0, 0, 0), 4f, picker);
    }

    private Deer createDeer(Vector3f position, EntityPicker picker) {
        return new Deer(texturedModels.get("deer"), position, new Vector3f(0, 0, 0), 2f, picker);
    }

    private Rabbit createRabbit(Vector3f position, EntityPicker picker) {
        return new Rabbit(texturedModels.get("rabbit"), position, new Vector3f(0, 0, 0), 0.5f, picker);
    }

    private Boar createBoar(Vector3f position, EntityPicker picker) {
        return new Boar(texturedModels.get("boar"), position, new Vector3f(0, 0, 0), 1f, picker);
    }

    private TexturedModel createTexturedModel(String name) {
        RawModel rawModel = OBJLoader.loadObjModel(name, loader);
        ModelTexture texture = new ModelTexture(loader.loadTexture(name + "-skin"));
        return new TexturedModel(rawModel, texture);
    }
}
