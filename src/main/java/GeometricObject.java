import java.util.List;
import javafx.scene.paint.Color;
// An object must implement a Renderable interface in order to
// be ray traced. Using this interface it is straight forward
// to add new objects
public interface GeometricObject {
    boolean intersect(Ray ray);
    Color Shade(Ray ray, java.util.List<Object> lights, List<Object> objects, Color bgnd);
    String toString();
}
