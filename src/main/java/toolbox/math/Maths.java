package toolbox.math;

import camera.Camera;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class Maths {

    public static float barryCentric(Vector3f p1, Vector3f p2, Vector3f p3, Vector2f pos) {
        float det = (p2.z - p3.z) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.z - p3.z);
        float l1 = ((p2.z - p3.z) * (pos.x - p3.x) + (p3.x - p2.x) * (pos.y - p3.z)) / det;
        float l2 = ((p3.z - p1.z) * (pos.x - p3.x) + (p1.x - p3.x) * (pos.y - p3.z)) / det;
        float l3 = 1.0f - l1 - l2;
        return l1 * p1.y + l2 * p2.y + l3 * p3.y;
    }

    public static Matrix4f createTransformationMatrix(
            Vector3f translation, float rx, float ry, float rz, float scale) {
        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();
        Matrix4f.translate(translation, matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(rx), new Vector3f(1, 0, 0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(ry), new Vector3f(0, 1, 0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(rz), new Vector3f(0, 0, 1), matrix, matrix);
        Matrix4f.scale(new Vector3f(scale, scale, scale), matrix, matrix);
        return matrix;
    }

    public static Matrix4f createViewMatrix(Camera camera) {
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.setIdentity();
        Matrix4f.rotate(
                (float) Math.toRadians(camera.getPitch()), new Vector3f(1, 0, 0), viewMatrix, viewMatrix);
        Matrix4f.rotate(
                (float) Math.toRadians(camera.getYaw()), new Vector3f(0, 1, 0), viewMatrix, viewMatrix);
        Vector3f cameraPos = camera.getPosition();
        Vector3f negativeCameraPos = new Vector3f(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        Matrix4f.translate(negativeCameraPos, viewMatrix, viewMatrix);
        return viewMatrix;
    }

    public static Matrix4f createTransformationMatrix(Vector2f translation, Vector2f scale) {
        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();
        Matrix4f.translate(translation, matrix, matrix);
        Matrix4f.scale(new Vector3f(scale.x, scale.y, 1f), matrix, matrix);
        return matrix;
    }

    public static float clamp(float value, float min, float max) {
        return Math.max(Math.min(value, max), min);
    }

    public static Vector3f sumTwoVectors(Vector3f firstVector, Vector3f secondVector) {
        Vector3f sumVector = new Vector3f();
        sumVector.x = firstVector.x + secondVector.x;
        sumVector.y = firstVector.y + secondVector.y;
        sumVector.z = firstVector.z + secondVector.z;
        return sumVector;
    }

    public static Vector3f subtractTwoVectors(Vector3f subtracted, Vector3f subtractor) {
        Vector3f sumVector = new Vector3f();
        sumVector.x = subtracted.x - subtractor.x;
        sumVector.y = subtracted.y - subtractor.y;
        sumVector.z = subtracted.z - subtractor.z;
        return sumVector;
    }

    public static Vector3f multiplyVectorByScale(Vector3f vector, float scale) {
        Vector3f multiplyVector = new Vector3f();
        multiplyVector.x = vector.x * scale;
        multiplyVector.y = vector.y * scale;
        multiplyVector.z = vector.z * scale;
        return multiplyVector;
    }

    public static Vector3f rotateVector(Vector3f vector, float rotation) {
        Vector4f toTransform = new Vector4f(vector.x, vector.y, vector.z, 1);
        Matrix4f matrix = new Matrix4f();
        Matrix4f.rotate((float) Math.toRadians(0), new Vector3f(1, 0, 0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(rotation), new Vector3f(0, 1, 0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(0), new Vector3f(0, 0, 1), matrix, matrix);
        Vector4f rotated = Matrix4f.transform(matrix, toTransform, toTransform);
        return new Vector3f(rotated.x, rotated.y, rotated.z);
    }

    public static Vector3f normalizeVector(Vector3f vectorToBeNormalized) {
        Vector3f vector = new Vector3f(vectorToBeNormalized.x, vectorToBeNormalized.y, vectorToBeNormalized.z);
        float length = (float) Math.sqrt(vector.x * vector.x + vector.y * vector.y + vector.z * vector.z);
        if (length > 0) {
            vector.x /= length;
            vector.y /= length;
            vector.z /= length;
        }
        return vector;
    }

    public static Vector3f clampVectorToValue(Vector3f vector, float min, float max) {
        float x = Math.max(min, Math.min(max, vector.x));
        float y = Math.max(min, Math.min(max, vector.y));
        float z = Math.max(min, Math.min(max, vector.z));
        return new Vector3f(x, y, z);
    }

    public static Vector3f changeVectorByValues(Vector3f vector, float value) {
        return new Vector3f(vector.x + value, vector.y + value, vector.z + value);
    }
}
