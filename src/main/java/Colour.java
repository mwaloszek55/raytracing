import java.util.List;
import javafx.scene.paint.Color;
public class Colour {
    private static final float TINY = 0.001f;
    Surface surface;
    public Colour(Surface surface) {
        this.surface = surface;
    }

    public Color Shading(Vector3D p, Vector3D n, Vector3D v, java.util.List<Object> lights, List<Object> objects, Color bgnd) {
        float r = 0;
        float g = 0;
        float b = 0;
        for (Object lightSources : lights) {
            Light light = (Light) lightSources;
            if (light.lightType == LightType.AMBIENT) {
                r += surface.ka * surface.ir * light.intensity.getRed();
                g += surface.ka * surface.ig * light.intensity.getGreen();
                b += surface.ka * surface.ib * light.intensity.getBlue();
            } else {
                Vector3D l;
                if (light.lightType == LightType.POINT) {
                    l = new Vector3D(light.lvec.point.subtract(p.point));
                    l.normalize();
                } else {
                    l = new Vector3D(light.lvec.point.multiply(-1));
                }

                // Check if the surface point is in shadow
                Vector3D poffset = new Vector3D(p.point.add(l.point.multiply(TINY)));
                Ray shadowRay = new Ray(poffset.point, l);
                if (shadowRay.trace(objects))
                    break;

                float lambert = n.dotProduct(l);
                if (lambert > 0) {
                    if (surface.kd > 0) {
                        float diffuse = surface.kd * lambert;
                        r += diffuse * surface.ir * light.intensity.getRed();
                        g += diffuse * surface.ig * light.intensity.getGreen();
                        b += diffuse * surface.ib * light.intensity.getBlue();
                    }
                    if (surface.ks > 0) {
                        lambert *= 2;
                        Vector3D vv = new Vector3D(n.point.multiply(lambert).subtract(l.point)); // changed
                        float spec = v.dotProduct(vv);
                        if (spec > 0) {
                            spec = surface.ks * ((float) Math.pow(spec, surface.ns));
                            r += spec * light.intensity.getRed();
                            g += spec * light.intensity.getGreen();
                            b += spec * light.intensity.getBlue();
                        }
                    }
                }
            }
        }

        // Compute illumination due to reflection
        if (surface.kr > 0) {
            float t = v.dotProduct(n);
            if (t > 0) {
                t *= 2;
                Vector3D reflect = new Vector3D(n.point.multiply(t).subtract(v.point));
                Vector3D poffset = new Vector3D(p.point.add(reflect.point.multiply(TINY)));
                Ray reflectedRay = new Ray(poffset.point, reflect);
                if (reflectedRay.trace(objects)) {
                    Color rcolor = reflectedRay.Shade(lights, objects, bgnd);
                    r += surface.kr * rcolor.getRed();
                    g += surface.kr * rcolor.getGreen();
                    b += surface.kr * rcolor.getBlue();
                } else {
                    r += surface.kr * bgnd.getRed();
                    g += surface.kr * bgnd.getGreen();
                    b += surface.kr * bgnd.getBlue();
                }
            }
        }

        // Add code for refraction here

        r = Math.min(r, 1f);
        g = Math.min(g, 1f);
        b = Math.min(b, 1f);

        r = (r < 0) ? 0 : r;
        g = (g < 0) ? 0 : g;
        b = (b < 0) ? 0 : b;

        return new Color(r, g, b, 1);
    }
}
