class Surface {
    public float ir, ig, ib;        // surface's intrinsic color
    public float ka, kd, ks, ns;    // constants for phong model
    public float kt, kr, nt;
    private static final float I255 = 0.00392156f;  // 1/255

    public Surface(float rval, float gval, float bval, float a, float d, float s, float n, float r, float t, float index) {
        this.ir = rval;
        this.ig = gval;
        this.ib = bval;
        this.ka = a;
        this.kd = d;
        this.ks = s;
        this.ns = n;
        this.kr = r*I255;
        this.kt = t;
        this.nt = index;
    }
}