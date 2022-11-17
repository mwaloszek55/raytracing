import javax.swing.*;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;
import java.awt.*;

public class api {
    //want to set up frame or display
    JFrame frame;
    int frameHeight, frameWidth;
    float horizontal;
    Point3D origin;
    Vector3D lookat, up;
    BufferedImage canvas;
    final static int CHUNKSIZE = 100;
    Color background;
    Vector3D Du, Dv, Vp;
    List<Object> objectList, lightList;
    Surface currentSurface;

    //not sure if this should have a constructor
    public api(int height, int width){
        this.frameHeight = height;
        this.frameWidth = width;
        this.horizontal = 0;
        this.canvas = new BufferedImage(this.frameWidth,this.frameHeight,BufferedImage.TYPE_INT_ARGB);
        objectList = new ArrayList<>(CHUNKSIZE);
        lightList = new ArrayList<>(CHUNKSIZE);
        currentSurface = new Surface(0.8f,0.2f,0.9f,0.2f,0.4f, 0.4f, 10.0f, 0f, 0f,1f);
    }

    public void setupFrame(){
        Dimension displaySize = new Dimension(this.frameWidth, this.frameHeight);

        frame = new JFrame("Ray Tracing Demonstration");
        frame.setSize(this.frameWidth,this.frameHeight);
        frame.setPreferredSize(displaySize);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        canvas = new BufferedImage(this.frameWidth,this.frameHeight,BufferedImage.TYPE_INT_ARGB);
        this.horizontal = 30;

        // Default values
        if (origin == null) origin = new Point3D(0,0,10);
        if (lookat == null) lookat = new Vector3D(0,0,0);
        if (up == null) up = new Vector3D(0,1,0);
        if (background ==null) background = new Color(0,0,0, 1);

        this.setupViewingMatrix();
    }
    public void setupViewingMatrix(){
        Vector3D look = new Vector3D(lookat.point.subtract(origin));
        Du = Vector3D.normalize(look.crossProduct(up));
        Dv = Vector3D.normalize(look.crossProduct(Du));
        float fl = (float)(this.frameWidth / (2*Math.tan((0.5*horizontal)*Math.PI/180)));
        Vp = Vector3D.normalize(look);
        Vp.point = Vp.point.multiply(fl).subtract(Du.point.multiply(this.frameWidth).add(Dv.point.multiply(this.frameHeight)).multiply(0.5f));
    }

    public void renderFrame() throws IOException {
        ImagePanel image = new ImagePanel("resources/Placeholder-01.png");

        frame.add(image);
        frame.pack();
        frame.setVisible(true);

        long time = System.currentTimeMillis();
        for (int j=0; j< this.frameHeight; j+=1){
            for (int i =0; i<this.frameWidth; i+=1){
                this.renderPixel(i,j);
            }
        }
        image.updateImage(this.canvas);
        time = System.currentTimeMillis() - time;
        System.err.println("Rendered in " +(time/60000)+ " minutes: "+((time%60000)*0.001)+" seconds" );
    }
    public void setLights(){

    }

    //can i get a better name than i and j here - wtf do they mean tho??
    // I think they are coordinates on the canvas. So we need to locate where to create that pixel
    public void renderPixel(int i, int j){
        Vector3D direction= new Vector3D(Du.point.multiply(i).add(Dv.point.multiply(j)).add(Vp.point));
        Ray ray = new Ray(origin,direction);
        Color pixelColour;

        if (ray.trace(this.objectList)){
            Color bg = background;
            pixelColour = ray.Shade(lightList, objectList, background);
        }
        else{
            pixelColour = background;
        }
        int r = ((int)pixelColour.RED.getRed()*255);
        int g = ((int)pixelColour.GREEN.getGreen() * 255);
        int b = ((int)pixelColour.BLUE.getBlue() * 255);
        int rgb = (r << 16) + (g << 8) + b;
        canvas.setRGB(i, j, rgb);
    }

    //want to create shapes etc
    // x, y and z are coordinates in 3D space
    public void createSphere(float x, float y, float z, float r){
        Point3D v = new Point3D(x,y,z);
        objectList.add(new Sphere(currentSurface,v,r));
    }

    public void setOrigin (float x, float y, float z){
        origin = new Point3D(x,y,z);
    }
    public void setLookat(float x, float y, float z){
        lookat = new Vector3D(x, y,z);
    }
    public void setUp(float x, float y, float z){
        up = new Vector3D(x, y, z);
    }

    // the 2 methods below are not used for now, should remove if we don't need it at all
    /*public void setHorizontal(float newHorizontal){
        this.horizontal = newHorizontal;
    }
    public void setBackgroundColour(int red, int green, int blue){
        background = new Color(red,green,blue);
    }*/
    public void setlight( float red, float green, float blue, String typeOfLight){
        Color intensity = new Color(red, green, blue, 1);
        typeOfLight = typeOfLight.toLowerCase();
        if (typeOfLight.equals("ambient")){
            lightList.add(new Light(LightType.AMBIENT, null, intensity));
        }
    }
    public void setlight(float red, float green, float blue, String typeOfLight, float x, float y, float z){
        typeOfLight = typeOfLight.toLowerCase();
        Color intensity = new Color(red, green, blue, 1);
        if (typeOfLight.equals("directional")) {
            Vector3D v = new Vector3D(x, y, z);
            lightList.add(new Light(LightType.DIRECTIONAL, v, intensity));
        } else if (typeOfLight.equals("point")) {
            Vector3D v = new Vector3D(x, y, z);
            lightList.add(new Light(LightType.POINT, v, intensity));
        } else {
            System.err.println("ERROR: incorrect input");
        }
    }

    public void setSurface(float red, float green, float blue, float ka, float kd, float ks, float ns, float kr, float kt, float index){
        currentSurface = new Surface(red, green, blue,ka, kd, ks, ns, kr, kt, index);
    }
}