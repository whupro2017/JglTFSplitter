package whu.splitter;

import de.javagl.obj.*;

import java.io.*;
import java.util.List;

public class GroupSplitter {
    private static final String dir = "E:\\Michael\\Work\\yl\\datasets\\luotian\\";

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // Read an OBJ file
        if (args.length == 0) {
            InputStream objInputStream = new FileInputStream("E:/Michael/Work/yl/datasets/luotian/model_textured.obj");
            Obj originalObj = ObjReader.read(objInputStream);
            Obj obj = ObjUtils.convertToRenderable(originalObj);
            List<Obj> listObjs = ObjSplitting.splitByMaxNumVertices(obj, 65535);
            System.out.println(listObjs.size());
            int idx = 0;
            for (Obj o : listObjs) {
                OutputStream out = new FileOutputStream(new File(dir + "/temp" + idx + ".obj"));
                ObjWriter.write(o, out);
                out.flush();
                out.close();
                BufferedReader in = new BufferedReader(new FileReader(dir + "/temp" + idx + ".obj"));
                BufferedWriter ot = new BufferedWriter(new FileWriter(dir + "/split" + idx + ".obj"));
                String line = "";
                while ((line = in.readLine()) != null) {
                    if (line.startsWith("mtllib")) {
                        ot.write(line + "\n");
                        String[] texture = line.split(" ");
                        String[] filename = texture[1].split("[.]+");
                        ot.write("usemtl " + filename[0] + "\n");
                    } else {
                        ot.write(line + "\n");
                    }
                }
                in.close();
                File ifile = new File(dir + "\\temp" + idx++ + ".obj");
                ifile.delete();
                ot.close();
            }
        } else {
            int pernum = 131072;
            InputStream objInputStream = new FileInputStream(args[0]);
            Obj originalObj = ObjReader.read(objInputStream);
            Obj obj = ObjUtils.convertToRenderable(originalObj);
            if (args.length == 3) {
                int total = obj.getNumVertices();
                pernum = total / Integer.parseInt(args[2]) + 1;
            }
            List<Obj> listObjs = ObjSplitting.splitByMaxNumVertices(obj, pernum);
            System.out.println(listObjs.size());
            int idx = 0;
            for (Obj o : listObjs) {
                OutputStream out = new FileOutputStream(new File(args[1] + "/temp" + idx + ".obj"));
                ObjWriter.write(o, out);
                out.flush();
                out.close();
                BufferedReader in = new BufferedReader(new FileReader(args[1] + "/temp" + idx + ".obj"));
                BufferedWriter ot = new BufferedWriter(new FileWriter(args[1] + "/split" + idx + ".obj"));
                String line = "";
                while ((line = in.readLine()) != null) {
                    if (line.startsWith("mtllib")) {
                        ot.write(line + "\n");
                        String[] texture = line.split(" ");
                        try {
                            String[] filename = texture[1].split("[.]+");
                            ot.write("usemtl " + filename[0] + "\n");
                        } catch (Exception e) {
                            System.out.println(texture[1]);
                            e.printStackTrace();
                        }
                    } else {
                        ot.write(line + "\n");
                    }
                }
                in.close();
                File ifile = new File(args[1] + "/temp" + idx++ + ".obj");
                ifile.delete();
                ot.close();
            }
        }
    }
}
