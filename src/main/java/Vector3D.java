public class Vector3D {
    Point3D point;

    // constructors
    public Vector3D(float x, float y, float z) {
        this.point = new Point3D(x, y, z);
    }
    public Vector3D(Point3D point) {
        this.point = point;
    }

    // methods
    // dot product of two vectors is the sum of the products of their corresponding components.
    public float dotProduct(Vector3D vector) {
        return (point.x*vector.point.x + point.y*vector.point.y + point.z*vector.point.z);
    }

    public Vector3D crossProduct(Vector3D vector) {
        return new Vector3D(((point.y * vector.point.z) - (point.z * vector.point.y)),
                ((point.z * vector.point.x) - (point.x * vector.point.z)),
                ((point.x * vector.point.y) - (point.y * vector.point.x)));
    }

    /**
     * This method normalizes two vectors to find the direction of the vector
     * when a vector is normalised, its magnitude changes
     *
     * @param vector defines a Vector3D object
     * @return new Vector3D object
     */
    public static Vector3D normalize(Vector3D vector) {
        float magnitude = magnitude(vector);
        if (magnitude != 0 && magnitude != 1) magnitude = (float) (1 / Math.sqrt(magnitude));
        return new Vector3D(vector.point = vector.point.multiply(magnitude));
    }

    public void normalize() {
        float magnitude = magnitude(this);
        if (magnitude != 0 && magnitude != 1) magnitude = (float) (1 / Math.sqrt(magnitude));
        new Vector3D(this.point = this.point.multiply(magnitude));
    }

    public static float magnitude(Vector3D vector) {
        return (vector.dotProduct(vector));
    }
}
