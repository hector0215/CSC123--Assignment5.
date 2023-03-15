//Hector Arroyo harroyoruiz1@toromail.csudh.edu
import java.io.*;

public class FileCopy {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Error: Missing arguments.");
            return;
        }

        String sourceFile = args[0];
        String targetFile = args[1];

        File src = new File(sourceFile);
        File tgt = new File(targetFile);

        if (!src.exists() || !src.isFile()) {
            System.out.println("Error: Source file does not exist or is not a file.");
            return;
        }

        if (tgt.exists()) {
            System.out.println("Error: Target file already exists.");
            return;
        }

        try {
            InputStream in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(tgt);
            byte[] buffer = new byte[1024];
            int length;

            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }

            in.close();
            out.close();

            System.out.println(src.length() + " bytes successfully copied from " + sourceFile + " to " + targetFile);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
