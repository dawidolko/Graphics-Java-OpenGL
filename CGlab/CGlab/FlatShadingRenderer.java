package CGlab;

public class FlatShadingRenderer extends Renderer {

    private Vec3f lightSource = new Vec3f(0.0f, 0.0f, 5.0f); // źródło światła "przed ekranem"
    
    private Vec3f eye = new Vec3f(0.0f, 2.75f, 3.0f); // kamera, lekko podniesiona 
    private Vec3f gaze = new Vec3f(0.0f, -0.3f, -1.0f);   // i skierowana nieco w dół
    private Vec3f viewUp = new Vec3f(0.0f, 1.0f, 0.0f);

    private Matrix4f Mcam;
    private Matrix4f Mper;
    private Matrix4f Mvp;

    private float fov = 45;
    private float far = -25;
    private float near = -1;

    protected boolean cullFace = true;

    public FlatShadingRenderer(String filename) { 
        super(filename);
    }
    public FlatShadingRenderer(String filename, int w, int h) { 
        super(filename, w, h);
    }

    public void backfaceCullingOff() {this.cullFace = false;}
    public void backfaceCullingOn() {this.cullFace = true;}

    public void zbufferTestOff() {this.zbufferTest = false;}
    public void zbufferTestOn() {this.zbufferTest = true;}

    private void computeCameraMatrix(){

        gaze.normalize();
        Vec3f w = new Vec3f(-gaze.x, -gaze.y, -gaze.z);
        Vec3f u = viewUp.cross(w);
        u.normalize();
        Vec3f v = w.cross(u);
        v.normalize();

        Matrix4f M1 = new Matrix4f(u.x, u.y, u.z, 0.0f,
                                   v.x, v.y, v.z, 0.0f, 
                                   w.x, w.y, w.z, 0.0f,
                                   0.0f,0.0f,0.0f,1.0f);

        Matrix4f M2 = new Matrix4f(1.0f, 0.0f, 0.0f, -eye.x,
                                   0.0f, 1.0f, 0.0f, -eye.y,
                                   0.0f, 0.0f, 1.0f, -eye.z,
                                   0.0f, 0.0f, 0.0f, 1.0f);

        Mcam = Matrix4f.multiply(M1, M2);
    }

    private void computePerspectiveMatrix() {
        float theta = (float)Math.toRadians(fov);
        float a = render.getWidth() / (float)render.getHeight();

        Mper = new Matrix4f(1 / (a * (float) Math.tan(theta / 2)), 0.0f, 0.0f, 0.0f,
                            0.0f, 1 / (float) Math.tan(theta / 2), 0.0f, 0.0f,
                            0.0f, 0.0f, -((far + near) / (far - near)), -((2 * far * near) / (far - near)),
                            0.0f, 0.0f, -1.0f, 0.0f);
    }

    private void computeViewportMatrix() {
        Mvp = new Matrix4f(render.getWidth() / 2.0f, 0.0f, 0.0f, render.getWidth() / 2.0f,
        0.0f, render.getHeight() / 2.0f, 0.0f, render.getHeight() / 2.0f,
        0.0f, 0.0f, 1.0f, 0.0f,
        0.0f, 0.0f, 0.0f, 1.0f);
    }
    public void render(Model model) {

        System.out.println("----------Rendering model----------");
        
        float avgZculled = 0.0f;
        float avgZdrawed = 0.0f;

        this.computeCameraMatrix();
        this.computePerspectiveMatrix();
        this.computeViewportMatrix();

        System.out.println("Camera Matrix:");
        System.out.println(Mcam.toString());
        System.out.println("Perspective Matrix:");
        System.out.println(Mper.toString());

        Matrix4f M = Matrix4f.multiply(Mper, Mcam);
        M = Matrix4f.multiply(Mvp, M);

        for (Vec3i face : model.getFaceList()) {

            Vec3f[] screen_coords = new Vec3f[3];
            Vec3f[] world_coords = new Vec3f[3];

            world_coords[0] = model.getVertex(face.x);
            world_coords[1] = model.getVertex(face.y);
            world_coords[2] = model.getVertex(face.z);

            for (int j=0; j<3; j++) {

                Vec4f sc = Matrix4f.multiply(M, new Vec4f(world_coords[j], 1.0f));
                sc.x /= sc.w;
                sc.y /= sc.w;

                screen_coords[j] = new Vec3f(sc.x, sc.y, sc.z);
            }
            
            Vec3f AB = Vec3f.sub(world_coords[1], world_coords[0]);
            Vec3f AC = Vec3f.sub(world_coords[2], world_coords[0]);

            AB.normalize();
            AC.normalize();

            Vec3f normVec = AB.cross(AC);

            if (cullFace) {
                Vec3f cameraToTriangleVector = Vec3f.sub(world_coords[0], eye);
                cameraToTriangleVector.normalize();
                if(normVec.dot(cameraToTriangleVector) > 0) {
                    avgZculled = 0.995f * avgZculled + 0.005f * world_coords[0].z;
                    continue;
                }
            }
        
            Vec3f toLightVector = Vec3f.sub(lightSource, world_coords[0]);
            toLightVector.normalize();

            int lightIntensity = (int)(255 * normVec.dot(toLightVector));
            lightIntensity = Math.max(0, Math.min(255, lightIntensity));

            int color = (255 << 24) | (lightIntensity << 16) | (lightIntensity << 8) | lightIntensity;
            drawTriangle(screen_coords[0], screen_coords[1], screen_coords[2], color);

            avgZdrawed = 0.995f * avgZdrawed + 0.005f * world_coords[0].z;
        }
        System.out.println("Approximate average depth of culled faces: " + avgZculled);
        System.out.println("Approximate average depth of drawed faces: " + avgZdrawed);
    }

}