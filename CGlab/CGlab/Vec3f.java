package CGlab;

public class Vec3f {
    public float x;
    public float y;
    public float z;

    public Vec3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3f(Vec2f xy, float z) {
        this.x = xy.x;
        this.y = xy.y;
        this.z = z;
    }
   
    public Vec3f cross(Vec3f other) {
        float crossX = this.y * other.z - this.z * other.y;
        float crossY = this.z * other.x - this.x * other.z;
        float crossZ = this.x * other.y - this.y * other.x;
        return new Vec3f(crossX, crossY, crossZ);
    }

    public float dot(Vec3f other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    public void sub(Vec3f vec) {
        this.x = this.x - vec.x;
        this.y = this.y - vec.y;
        this.z = this.z - vec.z;
    }

    public static Vec3f sub(Vec3f a, Vec3f b) {
        float subX = a.x - b.x;
        float subY = a.y - b.y;
        float subZ = a.z - b.z;
        return new Vec3f(subX, subY, subZ);

    }

    public void add(Vec3f vec) {
        this.x += vec.x;
        this.y += vec.y;
        this.z += vec.z;
    }

    public static Vec3f add(Vec3f a, Vec3f b) {
        float sumX = a.x + b.x;
        float sumY = a.y + b.y;
        float sumZ = a.z + b.z;
        return new Vec3f(sumX, sumY, sumZ);
    }

    public void normalize() {
        float length = (float) Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
        this.x /= length;
        this.y /= length; 
        this.z /= length;
    }

    @Override
    public String toString() {
        return x + " " + y + " " + z;
    }
}
