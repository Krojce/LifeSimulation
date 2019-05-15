package toolbox.picking;

import camera.Camera;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import toolbox.collision.Collider;
import toolbox.math.Maths;

public class EntityPicker extends Raycast {
    public EntityPicker(Camera cam, Matrix4f projection) {
        super(cam, projection);
    }

    public boolean checkIfPicked(Collider collider, Vector3f terrainPosition) {
        return Maths.isSphereIntersected(terrainPosition, collider.getRadius(), collider.getCenter());
    }
}
