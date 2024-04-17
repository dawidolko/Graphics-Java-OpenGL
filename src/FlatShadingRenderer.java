import java.io.IOException;

public class FlatShadingRenderer extends Renderer {

    public int maxDepth = 1000;

    public FlatShadingRenderer(String filename, int w, int h) {
        super(filename, w, h);
    }

    public void render(Model model) throws IOException {

        var lightVector = new Vec3f(0, 0, 1);
        var cameraVector = new Vec3f(0, 0, 1);

        for (Vec3i face : model.getFaceList()) {

            Vec3i[] screen_coords = new Vec3i[3];
            Vec3f[] world_coords = new Vec3f[3];

            world_coords[0] = model.getVertex(face.x);
            world_coords[1] = model.getVertex(face.y);
            world_coords[2] = model.getVertex(face.z);

            Vec3f normalVector = new Vec3f(0 ,0 ,0);
            var v1 = world_coords[0];
            var v2 = world_coords[1];
            var v3 = world_coords[2];
            normalVector.x = (v2.y - v1.y) * (v3.z - v1.z) - (v2.z - v1.z) * (v3.y - v1.y);
            normalVector.y = (v2.z - v1.z) * (v3.x - v1.x) - (v2.x - v1.x) * (v3.z - v1.z);
            normalVector.z = (v2.x - v1.x) * (v3.y - v1.y) - (v2.y - v1.y) * (v3.x - v1.x);
            float length = normalVector.length();
            normalVector.x /= length;
            normalVector.y /= length;
            normalVector.z /= length;

            float cameraValue = normalVector.x * cameraVector.x + normalVector.y * cameraVector.y + normalVector.z * cameraVector.z;
            if (cameraValue>=0) {

                float intensity = normalVector.x * lightVector.x + normalVector.y * lightVector.y + normalVector.z * lightVector.z;

                int greySample = (int)(255 * intensity);
                if (intensity<0) greySample = 0;

                for (int j = 0; j < 3; j++) {
                    screen_coords[j] = new Vec3i((int) ((world_coords[j].x + 1.0) * render.getWidth() / 2.0),
                            (int) ((world_coords[j].y + 1.0) * render.getHeight() / 2.0) - render.getHeight() / 2,
                            (int) ((world_coords[j].z + 1.0) * 0.5 * maxDepth));
                }

                int color = 255 << 24 | greySample << 16 | greySample << 8 | greySample;

                drawTriangle(screen_coords[0], screen_coords[1], screen_coords[2], color);
            }
        }
    }
}