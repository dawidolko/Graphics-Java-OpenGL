package CGlab;

public class Vec4f {
    public float x;
    public float y;
    public float z;
    public float w;

    public Vec4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vec4f(Vec2f xy, float z, float w) {
        this.x = xy.x;
        this.y = xy.y;
        this.z = z;
        this.w = w;
    }

    public Vec4f(Vec3f xyz, float w) {
        this.x = xyz.x;
        this.y = xyz.y;
        this.z = xyz.z;
        this.w = w;
    }
   
    // Iloczyn wektorowy dla wektorów 3D we współrzędnych homogenicznych.
    // Nie jest to iloczyn wektorów 4D bo ten jest niezdefiniowany, patrz:
    // WS Massey (1983). "Cross products of vectors in higher dimensional
    // Euclidean spaces". The American Mathematical Monthly. 90 (10): 697–701. 
    public Vec4f cross3D(Vec4f other) {
        float crossX = this.y * other.z - this.z * other.y;
        float crossY = this.z * other.x - this.x * other.z;
        float crossZ = this.x * other.y - this.y * other.x;
        // Składowa w wyniku iloczynu wektorowego jest ustawiana na 0
        return new Vec4f(crossX, crossY, crossZ, 0);
    }

    public float dot(Vec4f other) {
        return this.x * other.x + this.y * other.y + this.z * other.z + this.w * other.w;
    }

    public void sub(Vec4f vec) {
        this.x -= vec.x;
        this.y -= vec.y;
        this.z -= vec.z;
        this.w -= vec.w;
    }

    public static Vec4f sub(Vec4f a, Vec4f b) {
        float subX = a.x - b.x;
        float subY = a.y - b.y;
        float subZ = a.z - b.z;
        float subW = a.w - b.w;
        return new Vec4f(subX, subY, subZ, subW);

    }

    public void add(Vec4f vec) {
        this.x += vec.x;
        this.y += vec.y;
        this.z += vec.z;
        this.w += vec.w;
    }

    public static Vec4f add(Vec4f a, Vec4f b) {
        float sumX = a.x + b.x;
        float sumY = a.y + b.y;
        float sumZ = a.z + b.z;
        float sumW = a.w + b.w;
        return new Vec4f(sumX, sumY, sumZ, sumW);
    }

    public void normalize() {
        float length = (float) Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w);
        this.x /= length;
        this.y /= length; 
        this.z /= length;
        this.w /= length;
    }

    @Override
    public String toString() {
        return x + " " + y + " " + z + " " + w;
    }
}
