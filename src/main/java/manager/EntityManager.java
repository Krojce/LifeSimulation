package manager;

import entity.template.BaseEntity;

import java.util.ArrayList;
import java.util.List;

public class EntityManager {

    private List<BaseEntity> entities = new ArrayList<BaseEntity>();

    public void update() {
        for (BaseEntity entity : entities) {
            entity.update();
        }
    }

    public void addEntity(BaseEntity entity) {
        entities.add(entity);
    }

    public void removeEntity(BaseEntity entity) {
        entities.remove(entity);
    }

    public List<BaseEntity> getEntities() {
        return entities;
    }
}
