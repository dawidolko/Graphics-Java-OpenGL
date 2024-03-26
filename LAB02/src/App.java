import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    String version = "0.02";

    // nie w src tylko w lab2

    // java -cp src App renderer.png 640 480 LINE_NAIVE

    public static void main(String[] args) {
        if (args.length < 4) {
            System.out.println("Usage: java App <path to output file> <width> <height> <line algorithm>");
            System.exit(1);
        }

        try {
            Renderer mainRenderer = new Renderer(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]), args[3]);
            mainRenderer.clear();
            mainRenderer.drawLine(300, 350, 50, 50, mainRenderer.getLineAlgo());
            mainRenderer.save();
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NumberFormatException e) {
            System.out.println("Width and height must be integers.");
            System.exit(1);
        }
    }
//    public static void main(String[] args) {
//        // Domyślne wartości
//        String defaultPath = "C:\\Users\\poczt\\Desktop\\OneDrive - Uniwersytet Rzeszowski\\Studia Dawida\\.STUDIA\\IV SEMESTR\\Grafika i komunukacja człowiek-komputer (GiKCz-K)\\LABY\\Lab2\\img\\rendered.png";
//        int defaultWidth = 640;
//        int defaultHeight = 480;
//        String defaultAlgo = "LINE_NAIVE";
//
//        // Ustawienie ścieżki i wymiarów na wartości domyślne lub podane przez użytkownika
//        String outputPath = args.length > 0 ? args[0] : defaultPath;
//        int width = args.length > 1 ? Integer.parseInt(args[1]) : defaultWidth;
//        int height = args.length > 2 ? Integer.parseInt(args[2]) : defaultHeight;
//        String algo = args.length > 3 ? args[3] : defaultAlgo;
//
//        try {
//            Renderer mainRenderer = new Renderer(outputPath, width, height, algo);
//            mainRenderer.clear();
//            mainRenderer.drawLine(300, 350, 50, 50, mainRenderer.getLineAlgo());
//            mainRenderer.save();
//        } catch (IOException ex) {
//            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (NumberFormatException e) {
//            System.out.println("Width and height must be integers.");
//            System.exit(1);
//        }
//    }
    public String getVersion() {
	return this.version;
    }

}
