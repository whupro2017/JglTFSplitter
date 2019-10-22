package whu.splitter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Statistics {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(args[0]));
        int vn = 0;
        int vtn = 0;
        int fn = 0;
        String line = "";
        while ((line = br.readLine()) != null) {
            if (line.startsWith("vt ")) {
                vtn++;
            } else if (line.startsWith("v ")) {
                vn++;
            } else if (line.startsWith("f ")) {
                fn++;
            }
        }
        br.close();
        System.out.println("v: " + vn + " vt: " + vtn + " f: " + fn);
    }
}
