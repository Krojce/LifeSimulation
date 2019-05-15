package entity.template;

import org.lwjgl.util.vector.Vector3f;

public interface EntityUpdate {
    void update();

    void handlePicking(Vector3f position);
}
