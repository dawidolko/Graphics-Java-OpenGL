package CGlab;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Renderer {

    public enum LineAlgo { NAIVE, BRESENHAM, BRESENHAM_INT; }

    public final int defaultHeight = 200;
    public final int defaultWidth = 200;

    protected BufferedImage render;
    protected String filename;
    protected LineAlgo lineAlgo = LineAlgo.BRESENHAM;

    protected float[][] zbuffer;
    protected boolean zbufferTest = true;

    public Renderer(String filename) {
        render = new BufferedImage(defaultWidth, defaultHeight, BufferedImage.TYPE_INT_ARGB);
        this.filename = filename;
        initZBuffer(defaultWidth, defaultHeight);
    }

    public Renderer(String filename, int width, int height) {
        render = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        this.filename = filename;
        initZBuffer(width, height);
    }

    public Renderer(String filename, int width, int height, LineAlgo lineAlgo) {
        render = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        this.filename = filename;
        this.lineAlgo = lineAlgo;
        initZBuffer(width, height);
    }

    protected void initZBuffer(int w, int h) {
        zbuffer = new float[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                zbuffer[i][j] = Float.POSITIVE_INFINITY;
            }
        }
    }

    public void drawPoint(int x, int y) {
        int white = 255 | (255 << 8) | (255 << 16) | (255 << 24);
        render.setRGB(x, y, white);
    }

    public void drawPoint(int x, int y, int color) {
        render.setRGB(x, y, color);
    }

    public void drawLine(int x0, int y0, int x1, int y1) {
        if(this.lineAlgo == LineAlgo.NAIVE) drawLineNaive(x0, y0, x1, y1);
        if(this.lineAlgo == LineAlgo.BRESENHAM) drawLineBresenham(x0, y0, x1, y1);
        if(this.lineAlgo == LineAlgo.BRESENHAM_INT) drawLineBresenhamInt(x0, y0, x1, y1);
    }

    public void drawLine(int x0, int y0, int x1, int y1, int color) {
        if(this.lineAlgo == LineAlgo.NAIVE) drawLineNaive(x0, y0, x1, y1, color);
        if(this.lineAlgo == LineAlgo.BRESENHAM) drawLineBresenham(x0, y0, x1, y1, color);
        if(this.lineAlgo == LineAlgo.BRESENHAM_INT) drawLineBresenhamInt(x0, y0, x1, y1, color);
    }

    public void drawLine(int x0, int y0, int x1, int y1, int color,  LineAlgo lineAlgo) {
        if(lineAlgo == LineAlgo.NAIVE) drawLineNaive(x0, y0, x1, y1, color);
        if(lineAlgo == LineAlgo.BRESENHAM) drawLineBresenham(x0, y0, x1, y1, color);
        if(lineAlgo == LineAlgo.BRESENHAM_INT) drawLineBresenhamInt(x0, y0, x1, y1, color);
    }

    public void drawLineNaive(int x0, int y0, int x1, int y1) {
        drawLineNaive(x0, y0, x1, y1, 0xffffffff);
    }

    public void drawLineNaive(int x0, int y0, int x1, int y1, int color) {

        // Najpierw odddzielnie obsługujemy przypadek pionowej lini, aby uniknąć przypadku dzielenia przez 0 i m = Inf
        // zakładamy przy tym, że można podać współrzędne y w dowolnej kolejności, niekoniecznie y0 < y1
        if(x0 == x1) {
            for(int y = Math.min(y0, y1); y <= Math.max(y0, y1); y++) {
                render.setRGB(x0, y, color);
            }
        }
        else {
            // jeśli punkty podane są w takiej kolejności, że x1 jest mniejsze od x0, to przed rysowaniem podmieniamy
            // punkty miejscami, aby iterować od x0 do x1. Czyli sprawdzamy koniec z początkiem.
            if(x1 < x0) {  
                int temp = x0;
                x0 = x1;
                x1 = temp;
                temp = y0;
                y0 = y1;
                y1 = temp;
            }

            // rysujemy
            int dx = x1 - x0;
            int dy = y1 - y0;
            float m = dy / (float)dx;
            float step = 0.1f; // Dla uniknięcia przerywanej linii dla przypadków m >> 1 powinniśmy iterować subpixelowo:
                               // dodawać do x mniej niż 1 pixel i wyznaczać y. Rozwiązuje to przypadek w którym dla stromej
                               // linii w miejscu jednego x powinnismy narysowac kilka pikseli o roznym y.
                               // Alternatywne podejście: porównać dx i dy i iterować po x lub po y.
            float y = y0;

            for(float x = x0; x <= x1; x = x + step) {           
                y = y + m * step;
                render.setRGB(Math.round(x), Math.round(y), color);
            }
        }
    }

    public void drawLineBresenham(int x0, int y0, int x1, int y1) {
        drawLineBresenham(x0, y0, x1, y1, 0xffffffff);
    }

    public void drawLineBresenham(int x0, int y0, int x1, int y1, int color) {
        // wybieramy czy iterujemy po x czy po y
        int dx_abs =  Math.abs(x1-x0);
        int dy_abs = Math.abs(y1-y0);
        if(dy_abs <= dx_abs) {
            // sprawdzamy koniec z początkiem, ewentualnie podmieniamy, patrz drawLineNaive
            if(x1 < x0) {  
                int temp = x0;
                x0 = x1;
                x1 = temp;
                temp = y0;
                y0 = y1;
                y1 = temp;
            }

            int dx = x1-x0;
            int dy = y1-y0;

            float derr = Math.abs(dy/(float)(dx));
            float err = 0;

            int y = y0;

            for (int x=x0; x<=x1; x++) {
                render.setRGB(x, y, color);
                err += derr;
                if (err > 0.5) {
                    y += (y1 > y0 ? 1 : -1);
                    err -= 1.;
                }
            } 
        }
        // iterujemy po y
        else {
            // sprawdzamy koniec z początkiem, ewentualnie podmieniamy
            if(y1 < y0) {  
                int temp = x0;
                x0 = x1;
                x1 = temp;
                temp = y0;
                y0 = y1;
                y1 = temp;
            }

            int dx = x1-x0;
            int dy = y1-y0;

            float derr = Math.abs(dx/(float)(dy));
            float err = 0;

            int x = x0;

            for (int y=y0; y<=y1; y++) {
                render.setRGB(x, y, color);
                err += derr;
                if (err > 0.5) {
                    x += (x1 > x0 ? 1 : -1);
                    err -= 1.;
                }
            } 
        }
    }

    public void drawLineBresenhamInt(int x0, int y0, int x1, int y1) {
        drawLineBresenhamInt(x0, y0, x1, y1, 0xffffffff);
    }

    public void drawLineBresenhamInt(int x0, int y0, int x1, int y1, int color) {
         // wybieramy czy iterujemy po x czy po y
         int dx_abs =  Math.abs(x1-x0);
         int dy_abs = Math.abs(y1-y0);
         if(dy_abs <= dx_abs) {
             // sprawdzamy koniec z początkiem, ewentualnie podmieniamy, patrz drawLineNaive
             if(x1 < x0) {  
                 int temp = x0;
                 x0 = x1;
                 x1 = temp;
                 temp = y0;
                 y0 = y1;
                 y1 = temp;
             }
 
             int dx = x1-x0;
             int dy = y1-y0;
 
             int derr = 2 * Math.abs(dy); // pomnożono przez 2 * dx aby uniknąć ułamka i float
             int err = 0;
 
             int y = y0;
 
             for (int x=x0; x<=x1; x++) {
                 render.setRGB(x, y, color);
                 err += derr;
                 if (err > dx) {
                     y += (y1 > y0 ? 1 : -1);
                     err -= 2 * dx;
                 }
             } 
         }
         // iterujemy po y
         else {
             // sprawdzamy koniec z początkiem, ewentualnie podmieniamy
             if(y1 < y0) {  
                 int temp = x0;
                 x0 = x1;
                 x1 = temp;
                 temp = y0;
                 y0 = y1;
                 y1 = temp;
             }
 
             int dx = x1-x0;
             int dy = y1-y0;
 
             int derr = 2 * Math.abs(dx);
             int err = 0;
 
             int x = x0;
 
             for (int y=y0; y<=y1; y++) {
                 render.setRGB(x, y, color);
                 err += derr;
                 if (err > dy) {
                     x += (x1 > x0 ? 1 : -1);
                     err -= 2 * dy;
                 }
             } 
         }
    }

    public Vec3f barycentric(Vec2f A, Vec2f B, Vec2f C, Vec2f P) {
        Vec3f v1 = new Vec3f(B.x - A.x, C.x - A.x, A.x - P.x); // tutaj potrzebujemy wektora składającego się ze współrzędnych
                                                               // x wektorów AB, AC ora PA. 
   
        Vec3f v2 = new Vec3f(B.y - A.y, C.y - A.y, A.y - P.y); // tutaj potrzebujemy wektora składającego się ze współrzędnych
                                                               // y wektorów AB, AC ora PA. 
        Vec3f cross = v1.cross(v2);
        Vec2f uv = new Vec2f(cross.x / cross.z, cross.y / cross.z); 
        Vec3f barycentric = new Vec3f(uv.x, uv.y, 1 - uv.x - uv.y);
        return barycentric;
   } 


   public void drawTriangle(Vec3f A, Vec3f B, Vec3f C, int color) {

        Vec2f A2d = new Vec2f(A.x, A.y);
        Vec2f B2d = new Vec2f(B.x, B.y);
        Vec2f C2d = new Vec2f(C.x, C.y);

        float minX = Math.min(A.x, Math.min(B.x, C.x));
        float minY = Math.min(A.y, Math.min(B.y, C.y));
        float maxX = Math.max(A.x, Math.max(B.x, C.x));
        float maxY = Math.max(A.y, Math.max(B.y, C.y));

        maxX = Math.min(maxX, this.render.getWidth() - 1);
        maxY = Math.min(maxY, this.render.getHeight() - 1);

        minX = Math.max(minX, 0);
        minY = Math.max(minY, 0);

        for(int y = (int)minY; y < maxY; y++) {
            for (int x = (int)minX; x < maxX; x++) {
                Vec2f P = new Vec2f(x, y);
                Vec3f barycentric = barycentric(A2d, B2d, C2d, P);
                if(barycentric.x >= 0 && barycentric.x <= 1 &&
                barycentric.y >= 0 && barycentric.y <= 1 &&
                barycentric.z >= 0 && barycentric.z <= 1) {

                    float Pz = A.z * barycentric.x + B.z * barycentric.y + C.z * barycentric.z;
                    if(zbufferTest == false) {      // jeśli nie testujemy z-bufora to po prostu rysuj
                        render.setRGB(x, y, color);
                        continue;
                    }
                    if( Pz < zbuffer[y][x]) {
                        render.setRGB(x, y, color);
                        zbuffer[y][x] = Pz;
                    }
                }
            }
        }
    } 


    public void drawTriangle(Vec2f A, Vec2f B, Vec2f C, int color) {

        float minX = Math.min(A.x, Math.min(B.x, C.x));
        float minY = Math.min(A.y, Math.min(B.y, C.y));
        float maxX = Math.max(A.x, Math.max(B.x, C.x));
        float maxY = Math.max(A.y, Math.max(B.y, C.y));

        for(int y = (int)minY; y < maxY; y++) {
            for (int x = (int)minX; x < maxX; x++) {
                Vec2f P = new Vec2f(x, y);
                Vec3f barycentric = barycentric(A, B, C, P);
                if(barycentric.x >= 0 && barycentric.x <= 1 &&
                   barycentric.y >= 0 && barycentric.y <= 1 &&
                   barycentric.z >= 0 && barycentric.z <= 1) {

                    render.setRGB(x, y, color);

                }
            }
        }
    } 

    public void drawTriangle(Vec2i A, Vec2i B, Vec2i C, int color) {
        drawTriangle(new Vec2f(A.x, A.y), new Vec2f(B.x, B.y), new Vec2f(C.x, C.y), color);
    } 

    public void save() throws IOException {
        File outputfile = new File(filename);
        render = Renderer.verticalFlip(render);
        ImageIO.write(render, "png", outputfile);
    }

    public void clear() {
        for (int x = 0; x < render.getWidth(); x++) {
            for (int y = 0; y < render.getHeight(); y++) {
                int black = 0 | (0 << 8) | (0 << 16) | (255 << 24);
                render.setRGB(x, y, black);
            }
        }
    }

    public static BufferedImage verticalFlip(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage flippedImage = new BufferedImage(w, h, img.getColorModel().getTransparency());
        Graphics2D g = flippedImage.createGraphics();
        g.drawImage(img, 0, 0, w, h, 0, h, w, 0, null);
        g.dispose();
        return flippedImage;
    }
}
