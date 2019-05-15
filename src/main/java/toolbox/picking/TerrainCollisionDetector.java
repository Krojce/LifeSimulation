package toolbox.picking;

import camera.Camera;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import terrain.Terrain;

public class TerrainCollisionDetector extends Raycast {

    private static final int RECURSION_COUNT = 200;
    private static final float RAY_RANGE = 600;
    private Vector3f currentTerrainPoint;

    public TerrainCollisionDetector(Camera camera, Matrix4f projection) {
        super(camera, projection);
    }

    @Override
    public void update() {
        super.update();
        if (intersectionInRange(0, RAY_RANGE, currentRay)) {
            currentTerrainPoint = binarySearch(0, 0, RAY_RANGE, currentRay);
        } else {
            currentTerrainPoint = null;
        }
    }

    private Vector3f binarySearch(int count, float start, float finish, Vector3f ray) {
        float half = start + ((finish - start) / 2f);
        if (count >= RECURSION_COUNT) {
            return getPointOnRay(ray, half);
        }
        if (intersectionInRange(start, half, ray)) {
            return binarySearch(count + 1, start, half, ray);
        } else {
            return binarySearch(count + 1, half, finish, ray);
        }
    }

    private boolean intersectionInRange(float start, float finish, Vector3f ray) {
        Vector3f startPoint = getPointOnRay(ray, start);
        Vector3f endPoint = getPointOnRay(ray, finish);
        return !isUnderGround(startPoint) && isUnderGround(endPoint);
    }

    private boolean isUnderGround(Vector3f testPoint) {
        float height = Terrain.getHeight(testPoint.getX(), testPoint.getZ());
        return testPoint.y < height;
    }

    private Vector3f getPointOnRay(Vector3f ray, float distance) {
        Vector3f camPos = Camera.getPosition();
        Vector3f start = new Vector3f(camPos.x, camPos.y, camPos.z);
        Vector3f scaledRay = new Vector3f(ray.x * distance, ray.y * distance, ray.z * distance);
        return Vector3f.add(start, scaledRay, null);
    }

    public Vector3f getCurrentTerrainPoint() {
        return currentTerrainPoint;
    }
}
