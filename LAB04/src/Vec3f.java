public class Vec3f {
    public float x;
    public float y;
    public float z;


    public Vec3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return x + " " + y + " " + z;
    }

    public float length() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }
}