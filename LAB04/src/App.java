import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    String version = "0.02";

    public static void main(String[] args) throws IOException {

        String defaultFilename = "output.png";
        int defaultWidth = 800;
        int defaultHeight = 600;
        String filename = args.length > 0 ? args[0] : defaultFilename;
        int width = args.length > 1 ? Integer.parseInt(args[1]) : defaultWidth;
        int height = args.length > 2 ? Integer.parseInt(args[2]) : defaultHeight;

        Model deerModel = new Model();
        deerModel.readOBJ("src/deer-mod.obj");

        FlatShadingRenderer mainRenderer = new FlatShadingRenderer(filename, width, height);
        mainRenderer.render(deerModel);

//        var TRANSLATE_VECTOR = new Vec3f(-300, 100, 500);
//
//        TRANSLATE_VECTOR.x /= mainRenderer.w;
//        TRANSLATE_VECTOR.y /= mainRenderer.h;
//        TRANSLATE_VECTOR.z /= mainRenderer.maxDepth;
//
//        deerModel.translate(TRANSLATE_VECTOR);
//
//        mainRenderer.render(deerModel);

        try {
            mainRenderer.save();
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getVersion() {
        return this.version;
    }
}