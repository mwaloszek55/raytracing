import java.awt.*;
import java.util.List;
import javafx.scene.paint.Color;
public class Sphere implements GeometricObject {
    Colour surface;
    public Point3D center;
    public float radius;
    public float radSqr;

    public Sphere(Surface surface, Point3D center, float radius) {
        this.surface = new Colour(surface);
        this.center = center;
        this.radius = radius;
        this.radSqr = radius*radius;
    }

    @Override
    public boolean intersect(Ray ray) {
        Vector3D displacement = new Vector3D(center.subtract(ray.origin));
        float dot = ray.direction.dotProduct(displacement);

        // Check if an intersection might be closer than a previous one
        if (dot - radius > ray.root)
            return false;

        // Test if ray intersects the sphere
        float t = radSqr + dot*dot - displacement.point.x*displacement.point.x - displacement.point.y*displacement.point.y - displacement.point.z*displacement.point.z;
        if (t < 0) {
            return false;
        }
        // Test if the intersection is in the positive
        // ray direction and it is the closest so far
        t = dot - ((float) Math.sqrt(t));
        if ((t > ray.root) || (t < 0)) {
            return false;
        }

        ray.root = t;
        ray.object = this;

        // if root value is positive, then there is an intersection between a ray and a surface
        return true;
    }

    @Override
    public Color Shade(Ray ray, List<Object> lights, List<Object> objects, Color bgnd) {
        Vector3D intersectionPoint = new Vector3D(ray.origin.add(ray.direction.point.multiply(ray.root)));
        Vector3D rayOrigin = new Vector3D(ray.direction.point.multiply(-1));
        Vector3D surfaceNormal = new Vector3D(intersectionPoint.point.subtract(center));
        surfaceNormal.normalize();

        return surface.Shading(intersectionPoint, surfaceNormal, rayOrigin, lights, objects, bgnd);
    }

    public String toString() {
        return ("Sphere (center, radius): " + center + ", " + radius);
    }
}