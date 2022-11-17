import java.awt.*;
import java.util.List;
import javafx.scene.paint.Color;
public class Ray {
    static final float MAX_T = Float.MAX_VALUE;
    Point3D origin;
    Vector3D direction;
    float root; // value of where the ray intersects the shape
    GeometricObject object;

    public Ray(Point3D origin, Vector3D direction) {
        this.origin = origin;
        this.direction = Vector3D.normalize(direction);
    }

    public boolean trace(List<Object> objects) {
        root = MAX_T;
        object = null;
        for (Object objList : objects) {
            GeometricObject object = (GeometricObject) objList;
            object.intersect(this);
        }
        return (object != null);
    }

    public final Color Shade(List<Object> lights, List<Object> objects, Color background) {
        return object.Shade(this, lights, objects, background);
    }

    public String toString() {
        return ("Ray origin = " + origin + " , direction = " + direction + "  root value = " + root);
    }
}

