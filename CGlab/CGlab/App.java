package CGlab;

import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    public static void main(String[] args) {
        if (args.length < 3 || args.length > 4) {
            System.out.println("Usage: java CGlab.App <path> <width> <height> [<lineAlgo>]");
            return;
        }

        String filePath = args[0];
        int width = 0;
        int height = 0;
        Renderer.LineAlgo lineAlgo = Renderer.LineAlgo.BRESENHAM; 

        try {
            width = Integer.parseInt(args[1]);
            height = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            System.out.println("Width and height must be integers.");
            return;
        }

        if (args.length == 4) {
            try {
                lineAlgo = Renderer.LineAlgo.valueOf(args[3]);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid line drawing algorithm. Valid options are: NAIVE, BRESENHAM, BRESENHAM_INT.");
                return;
            }
        }

        // dla lab 02 i 03 zwykły renderer:
        //Renderer mainRenderer = new Renderer(filePath, width, height, lineAlgo);   
        //mainRenderer.clear();

        // lab 04 i 05:
        FlatShadingRenderer mainRenderer = new FlatShadingRenderer(filePath, width, height); 
        mainRenderer.clear();

       // testy dla lab 02
       // testDrawingLines(mainRenderer, width/3, height/2); 
       // testDrawingColoredLines(mainRenderer, 2*width/3, height/2);
       
       // testy dla lab 03
       // testDrawingTriangles(mainRenderer, width, height);

       Model model01 = new Model();
       Model model02 = new Model();

       try {
            model01.readOBJ("/Users/mike/Downloads/deer-mod.obj"); // tutaj ustaw ścieżkę!
            model02.readOBJ("/Users/mike/Downloads/deer-mod.obj"); // tu też 
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // mainRenderer.backfaceCullingOff();  // tu sobie można sprawdzić co się stanie jak nie zrobimy 
        // mainRenderer.zbufferTestOff();      // backface culling albo testu z-buffora (lub obu)
        
        model01.translate(new Vec3f(0.0f, 0.0f, -0.5f)); // odsuwamy od kamery nieco pierwszy model
        model02.translate(new Vec3f(0.75f, 0.0f, -5.0f)); // drugi odsuwamy bardziej i w prawo

        mainRenderer.render(model01);
        mainRenderer.render(model02);
        
        try {
            mainRenderer.save();
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Funkcje testujące poprawność rozwiązania laboratorium 2 i 3:
    // testDrawingLines - rysuje pęk lini (odcinków), 72 w różnych kierunkach
    // testDrawingColoredLines - jak wyżej, ale w kolorze
    // testDrawingTriangles - dzieli sobie obraz na 20 komórek i w każdej z nich rysuje losowy trójkąt,
    //                        dodatkowo wierzchołki trójkąta są zaznaczone kontrastowym kolorem aby można
    //                        było wizualnie ocenić poprawność rysowania
    public static void testDrawingLines(Renderer mainRenderer, int centerX, int centerY) {
        int length = 100;
        int numLines = 72; 
    
        for (int i = 0; i < numLines; i++) {
            double angle = Math.toRadians((360.0 / numLines) * i);
            int endX = centerX + (int) (length * Math.cos(angle));
            int endY = centerY + (int) (length * Math.sin(angle));
            mainRenderer.drawLine(centerX, centerY, endX, endY);
        }
    }

    public static void testDrawingColoredLines(Renderer mainRenderer, int centerX, int centerY) {
        int length = 100;
        int numLines = 72;
    
        for (int i = 0; i < numLines; i++) {
            double angle = Math.toRadians((360.0 / numLines) * i);
            int endX = centerX + (int) (length * Math.cos(angle));
            int endY = centerY + (int) (length * Math.sin(angle));
            
            float ratio = (float) i / numLines;
            int red = (int) (255 * Math.abs(Math.sin(Math.PI * ratio)));
            int green = (int) (255 * Math.abs(Math.sin(Math.PI * ratio + Math.PI / 3)));
            int blue = (int) (255 * Math.abs(Math.sin(Math.PI * ratio + 2 * Math.PI / 3)));
            int alpha = 255;
            int color = (alpha << 24) | (red << 16) | (green << 8) | blue;
    
            mainRenderer.drawLine(centerX, centerY, endX, endY, color);
        }
    }
    
    public static void testDrawingTriangles(Renderer mainRenderer, int width, int height) {
        int numTriangles = 20; 
        int rows = 4; 
        int cols = 5;
        int cellWidth = width / cols;
        int cellHeight = height / rows;
        Random random = new Random();
    
        for (int i = 0; i < numTriangles; i++) {
            int row = i / cols;
            int col = i % cols;
            int offsetX = col * cellWidth;
            int offsetY = row * cellHeight;
    
            float xA = offsetX + random.nextInt(cellWidth);
            float yA = offsetY + random.nextInt(cellHeight);
            Vec2f A = new Vec2f(xA, yA);
    
            float xB = offsetX + random.nextInt(cellWidth);
            float yB = offsetY + random.nextInt(cellHeight);
            Vec2f B = new Vec2f(xB, yB);
    
            float xC = offsetX + random.nextInt(cellWidth);
            float yC = offsetY + random.nextInt(cellHeight);
            Vec2f C = new Vec2f(xC, yC);
    
            mainRenderer.drawTriangle(A, B, C, 0xff00ffff);
    
            mainRenderer.drawPoint((int)A.x, (int)A.y, 0xffff0000);
            mainRenderer.drawPoint((int)B.x, (int)B.y, 0xffff0000);
            mainRenderer.drawPoint((int)C.x, (int)C.y, 0xffff0000);
        }
    }
}
