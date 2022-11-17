public class Point3D {
    public float x, y, z;
    public Point3D(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Point3D add(Point3D point) {
        return new Point3D(x + point.x, y + point.y, z + point.z);
    }

    public Point3D multiply(float number) {
        return new Point3D(x * number, y * number, z * number);
    }

    public Point3D subtract(Point3D point) {
        return new Point3D(x - point.x, y - point.y, z - point.z);
    }

    // returns all points
    public String toString() {
        return "Point coordinates: [" + x + ", " + y + ", " + z + "]";
    }
}
