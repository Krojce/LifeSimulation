package systems;

import componentArchitecture.EntityManager;
import components.ModelComponent;
import manager.RenderManager;

import java.util.Set;
import java.util.UUID;

public class RenderSystem {
    private EntityManager entityManager;
    private RenderManager renderManager;

    public RenderSystem(EntityManager entityManager, RenderManager renderManager) {
        this.entityManager = entityManager;
        this.renderManager = renderManager;
    }

    public void update() {
        Set keySet = entityManager.getAllEntitiesPossessingComponent(ModelComponent.class);
        Object[] entities = keySet.toArray();
        for (Object object : entities) {
            renderManager.proccessEntity((UUID) object, entityManager.getComponent((UUID) object, ModelComponent.class).getTexturedModel());
        }
    }

}
