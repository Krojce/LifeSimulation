package manager;

import entity.template.BaseEntity;
import model.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

import java.util.*;

public class EntityManager {

    private static Map<TexturedModel, List<BaseEntity>> entities = new HashMap<TexturedModel, List<BaseEntity>>();

    public EntityManager() {
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                update();
            }
        }, 0, 100);
    }

    public void update() {
        for (Map.Entry<TexturedModel, List<BaseEntity>> entry : entities.entrySet()) {
            for (BaseEntity entity : entry.getValue()) {
                entity.update();
            }
        }
    }

    public static void removeEntity(BaseEntity baseEntity) {
        for (Map.Entry<TexturedModel, List<BaseEntity>> entry : entities.entrySet()) {
            entry.getValue().remove(baseEntity);
        }
    }

    public void addEntity(BaseEntity entity) {
        TexturedModel entityModel = entity.getTexturedModel();
        List<BaseEntity> batch = entities.get(entityModel);
        if (batch != null) {
            batch.add(entity);
        } else {
            List<BaseEntity> newBatch = new ArrayList<BaseEntity>();
            newBatch.add(entity);
            entities.put(entityModel, newBatch);
        }
    }

    public static Map<TexturedModel, List<BaseEntity>> getEntities() {
        return entities;
    }

    public void clearWorld() {
        entities.clear();
    }

    public void handlePicking(Vector3f currentPoint) {
        for (Map.Entry<TexturedModel, List<BaseEntity>> entry : entities.entrySet()) {
            for (BaseEntity entity : entry.getValue()) {
                entity.handlePicking(currentPoint);
            }
        }
    }
}
