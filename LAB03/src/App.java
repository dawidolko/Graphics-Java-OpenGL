import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    String version = "0.02";

    public static void main(String[] args) {

        if (args.length < 4) {
            System.out.println("Użycie: java App <nazwaPliku> <szerokość> <wysokość> <algorytm>");
            return; // Zakończ main jeśli nie ma wystarczającej liczby argumentów
        }

//        java -cp src App triangle.png 640 480 LINE_NAIVE
        // nie w src tylko w lab3 ! ! !

        Renderer mainRenderer = new Renderer(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]), args[3]);
        mainRenderer.clear();

        int x1 = 40; int y1 = 30;
        int x2 = 250; int y2 = 110;
        int x3 = 140; int y3 = 270;
        var vec1 = new Vec2f(x1, y1);
        var vec2 = new Vec2f(x2, y2);
        var vec3 = new Vec2f(x3, y3);

        mainRenderer.drawTriangle(vec1, vec2, vec3, new Vec3f(100, 200, 150));

        try {
            mainRenderer.save();
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//    public static void main(String[] args) {
//
//        // Domyślne wartości
//        String defaultFilename = "triangle.png";
//        int defaultWidth = 640;
//        int defaultHeight = 480;
//        String defaultAlgorithm = "LINE_NAIVE";
//
//        // Wartości przekazane przez użytkownika lub domyślne
//        String filename = args.length > 0 ? args[0] : defaultFilename;
//        int width = args.length > 1 ? Integer.parseInt(args[1]) : defaultWidth;
//        int height = args.length > 2 ? Integer.parseInt(args[2]) : defaultHeight;
//        String algorithm = args.length > 3 ? args[3] : defaultAlgorithm;
//
//        Renderer mainRenderer = new Renderer(filename, width, height, algorithm);
//        mainRenderer.clear();
//
//        int x1 = 40; int y1 = 30;
//        int x2 = 250; int y2 = 110;
//        int x3 = 140; int y3 = 270;
//        var vec1 = new Vec2f(x1, y1);
//        var vec2 = new Vec2f(x2, y2);
//        var vec3 = new Vec2f(x3, y3);
//
//        mainRenderer.drawTriangle(vec1, vec2, vec3, new Vec3f(100, 200, 150));
//
//        try {
//            mainRenderer.save();
//        } catch (IOException ex) {
//            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    public String getVersion() {
        return this.version;
    }

}