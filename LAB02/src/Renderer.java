import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class  Renderer {

    public enum LineAlgo { NAIVE, DDA, BRESENHAM, BRESENHAM_INT; }

    private BufferedImage render;
    public final int h;
    public final int w;

    private String filename;
    private Renderer.LineAlgo lineAlgo;

    public Renderer(String filename, int width, int height, String algo) {
        this.w = width;
        this.h = height;
        render = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        this.filename = filename;

        if (algo.equals("LINE_NAIVE")) lineAlgo = LineAlgo.NAIVE;
        if (algo.equals("BRESENHAM")) lineAlgo = LineAlgo.BRESENHAM;
        if (algo.equals("BRESENHAM_INT")) lineAlgo = LineAlgo.BRESENHAM_INT;
    }

    public LineAlgo getLineAlgo() {
        return lineAlgo;
    }

    public void drawPoint(int x, int y) {
        int white = 255 | (255 << 8) | (255 << 16) | (255 << 24);
        render.setRGB(x, y, white);
    }

    public void drawLine(int x0, int y0, int x1, int y1, LineAlgo lineAlgo) {
        if(lineAlgo == LineAlgo.NAIVE) drawLineNaive(x0, y0, x1, y1);
        if(lineAlgo == LineAlgo.DDA) drawLineDDA(x0, y0, x1, y1);
        if(lineAlgo == LineAlgo.BRESENHAM) drawLineBresenham(x0, y0, x1, y1);
        if(lineAlgo == LineAlgo.BRESENHAM_INT) drawLineBresenhamInt(x0, y0, x1, y1);
    }

    public void drawLineNaive(int x0, int y0, int x1, int y1) {
        double a = (y1*1.0-y0*1.0)/(x1*1.0-x0*1.0);
        double b = y1*1.0 - a*x1;

        int white = 255 | (255 << 8) | (255 << 16) | (255 << 24);

        if (x0-x1 != 0 && a!=0) {

            System.out.println("f(x) = " + a + "x + " + b);

            for (double i = Math.min(x0, x1); i < Math.max(x0, x1); i+=(1/a)) {

                int y = (int) Math.round(a * i + b);

                if (y >= Math.min(y0, y1) && y <= Math.max(y0, y1)) render.setRGB((int) Math.round(i), y, white);
            }
        } else  if (x0-x1==0) {
            for (int i=Math.min(y0,y1); i<Math.max(y0, y1); i++) {
                render.setRGB(x0, i, white);
            }
        } else {
            for (int i=Math.min(x0,x1); i<Math.max(x0, x1); i++) {
                render.setRGB(i, y0, white);
            }
        }
    }

    public void drawLineDDA(int x0, int y0, int x1, int y1) {
        // TODO: zaimplementuj
    }

    public void drawLineBresenham(int x0, int y0, int x1, int y1) {
        int white = 255 | (255 << 8) | (255 << 16) | (255 << 24);

        int dx = x1-x0;
        int dy = y1-y0;
        float derr = Math.abs(dy/(float)(dx));
        float err = 0;

        int y = y0;

        for (int x=x0; x<=x1; x++) {
            render.setRGB(x, y, white);
            err += derr;
            if (err > 0.5) {
                y += (y1 > y0 ? 1 : -1);
                err -= 1.;
            }
        } // Oktanty: 7, 8
    }

    public void drawLineBresenhamInt(int x0, int y0, int x1, int y1) {
        int white = 255 | (255 << 8) | (255 << 16) | (255 << 24);

        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;
        int err = dx - dy;

        while (x0 != x1 || y0 != y1) {
            render.setRGB(x0, y0, white);
            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x0 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y0 += sy;
            }
        }
    }

    public void save() throws IOException {
        File outputfile = new File(filename);
        render = Renderer.verticalFlip(render);
        ImageIO.write(render, "png", outputfile);
    }

    public void clear() {
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
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
