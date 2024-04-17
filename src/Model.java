import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Model {
    private ArrayList < Vec3f > vertexList;
    private ArrayList < Vec3i > faceList;
    public Model() {}

    public List < Vec3i > getFaceList() {
        return faceList;
    }
    public Vec3f getVertex(int index) {
        return vertexList.get(index);
    }

    public void readOBJ(String path) throws IOException {
        vertexList = new ArrayList < > ();
        faceList = new ArrayList < > ();
        InputStream objInputStream = new FileInputStream(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(objInputStream));
        vertexList.add(new Vec3f(0, 0, 0));

        while (reader.ready()) {
            String line = reader.readLine();
            if (isVertex(line)) vertexList.add(parseVertexFromOBJ(line));
            else if (isFace(line)) faceList.add(parseFaceFromOBJ(line));
        }
    }

    public void translate(Vec3f vector) {
        for (Vec3f e : vertexList) {
            e.x += vector.x;
            e.y += vector.y;
            e.z += vector.z;
        }
    }

    private boolean isVertex(String line) {
        return line.charAt(0) == 'v' && line.charAt(1) == ' ';
    }

    private boolean isFace(String line) {
        return line.charAt(0) == 'f' && line.charAt(1) == ' ';
    }

    private Vec3f parseVertexFromOBJ(String line) {
        String[] splitted = line.split(" "); // will be {"v", xcord, ycord, zcord} return new Vec3f( Float.parseFloat(splitted[1]), Float.parseFloat(splitted[2]),
        Float.parseFloat(splitted[3]);
        return new Vec3f( Float.parseFloat(splitted[1]), Float.parseFloat(splitted[2]), Float.parseFloat(splitted[3]));
    }

    private Vec3i parseFaceFromOBJ(String line) {
        String[] splitted = line.split(" "); // "f" will be first, than 3 x vertex_index/texture_index/normal_index

        return new Vec3i(Integer.parseInt(splitted[1].split("/")[0]), Integer.parseInt(splitted[2].split("/")[0]),
                Integer.parseInt(splitted[3].split("/")[0])); // we need to split based on "/" to get vertex_index
    }
}