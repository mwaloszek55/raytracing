// All the public variables here are ugly, but I
// wanted Lights and Surfaces to be "friends"
import javafx.scene.paint.Color;

public class Light {

    public LightType lightType;
    public Vector3D lvec;           // the position of a point light or
    // the direction to a directional light
    public Color intensity;

    public Light(LightType lightType, Vector3D v, Color intensity) {
        this.lightType = lightType;
        this.intensity = intensity;
        if (lightType != LightType.AMBIENT) {
            lvec = v;
            if (lightType == LightType.DIRECTIONAL) {
                lvec.normalize();
            }
        }
    }
}


/*
ka =  ambient reflection coefficient
kd = diffuse reflection coefficient
ks = specular reflection coefficient
kt = transmission coefficient
kr = reflectance coefficient
ns phong exponent
 */
