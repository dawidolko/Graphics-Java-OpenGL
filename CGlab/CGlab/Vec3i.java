package CGlab;

public class Vec3i {
    public int x;
    public int y;
    public int z;

    public Vec3i(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3i cross(Vec3i other) {
        int crossX = this.y * other.z - this.z * other.y;
        int crossY = this.z * other.x - this.x * other.z;
        int crossZ = this.x * other.y - this.y * other.x;
        return new Vec3i(crossX, crossY, crossZ);
    }

    public int dot(Vec3i other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    @Override
    public String toString() {
        return x + " " + y + " " + z;
    }    
}
