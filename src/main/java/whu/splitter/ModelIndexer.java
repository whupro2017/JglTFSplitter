package whu.splitter;

import de.javagl.obj.Obj;
import de.javagl.obj.ObjData;
import de.javagl.obj.ObjReader;
import de.javagl.obj.ObjUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class ModelIndexer {
    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        String path = "E:\\Michael\\Work\\yl\\datasets\\luotian\\model_textured.obj";
        if (args.length > 0) {
            path = args[0];
        }
        // Read an OBJ file
        InputStream objInputStream = new FileInputStream(path);
        Obj obj = ObjReader.read(objInputStream);

        // Prepare the Obj so that it contains unique texture coordinates
        obj = ObjUtils.makeTexCoordsUnique(obj);

        // Convert the Obj into a vertex-indexed Obj
        obj = ObjUtils.makeVertexIndexed(obj);

        // Obtain the indices of the elements for the faces
        int faceVertexIndices[] = ObjData.getFaceVertexIndicesArray(obj);
        int faceTexCoordIndices[] = ObjData.getFaceTexCoordIndicesArray(obj);
        int faceNormalIndices[] = ObjData.getFaceNormalIndicesArray(obj);

        System.out.println("Face vertex indices:");
        System.out.println(Arrays.toString(faceVertexIndices));

        System.out.println("Face texture coordinate indices:");
        System.out.println(Arrays.toString(faceTexCoordIndices));

        System.out.println("Face normal indices:");
        System.out.println(Arrays.toString(faceNormalIndices));

        // Obtain the data of the vertices
        float vertices[] = ObjData.getVerticesArray(obj);
        float texCoords[] = ObjData.getTexCoordsArray(obj, 2);
        float normals[] = ObjData.getNormalsArray(obj);

        System.out.println("Vertices:");
        System.out.println(createString(vertices, 3));

        System.out.println("Texture coordinates:");
        System.out.println(createString(texCoords, 2));

        System.out.println("Normals:");
        System.out.println(createString(normals, 3));
    }

    private static String createString(float array[], int stride) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i += stride) {
            for (int j = 0; j < stride; j++) {
                if (j > 0) {
                    sb.append(", ");
                }
                sb.append(array[i + j]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}