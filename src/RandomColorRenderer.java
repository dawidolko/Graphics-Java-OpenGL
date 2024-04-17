import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class RandomColorRenderer extends Renderer {

    public RandomColorRenderer(String filename, int w, int h) {
        super(filename, w, h);
    }

    public void render(Model model) throws IOException {

        for (Vec3i face : model.getFaceList()) {

            Vec3i[] screen_coords = new Vec3i[3];
            Vec3f[] world_coords = new Vec3f[3];

            world_coords[0] = model.getVertex(face.x);
            world_coords[1] = model.getVertex(face.y);
            world_coords[2] = model.getVertex(face.z);

            for (int j=0; j<3; j++) {
                screen_coords[j] = new Vec3i((int)((world_coords[j].x + 1.0) * render.getWidth() / 2.0),
                        (int)((world_coords[j].y + 1.0) * render.getHeight() / 2.0) - render.getHeight() / 2, 0);
            }
            int randColor = ThreadLocalRandom.current().nextInt(0, 0x00ffffff) | 0xff000000;
            drawTriangle(screen_coords[0], screen_coords[1], screen_coords[2], randColor);
        }
    }
}