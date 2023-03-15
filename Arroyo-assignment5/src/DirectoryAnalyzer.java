import java.io.*;

public class DirectoryAnalyzer {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Error: Missing directory name argument.");
            return;
        }

        String dirName = args[0];

        File dir = new File(dirName);

        if (!dir.exists()) {
            System.out.println("Error: Directory does not exist.");
            return;
        }

        if (!dir.isDirectory()) {
            System.out.println("Error: Input is not a directory.");
            return;
        }

        if (!dir.canRead()) {
            System.out.println("Error: No permission to read directory.");
            return;
        }

        int totalFiles = 0;
        int totalAlphaChars = 0;
        int totalNumericChars = 0;
        int totalSpaceChars = 0;
        long totalSize = 0;

        System.out.println("File Name\tSize\tAlpha Chars\tNumeric Chars\tSpaces");

        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                totalFiles++;

                int alphaChars = 0;
                int numericChars = 0;
                int spaceChars = 0;

                try {
                    InputStream in = new FileInputStream(file);
                    byte[] buffer = new byte[1024];
                    int length;

                    while ((length = in.read(buffer)) > 0) {
                        for (int i = 0; i < length; i++) {
                            char c = (char) buffer[i];

                            if (Character.isLetter(c)) {
                                alphaChars++;
                            } else if (Character.isDigit(c)) {
                                numericChars++;
                            } else if (Character.isWhitespace(c)) {
                                spaceChars++;
                            }
                        }
                    }

                    in.close();
                } catch (IOException e) {
                    System.out.println("Error: " + e.getMessage());
                }

                String size = getSizeString(file.length());

                System.out.printf("%s\t%s\t%d\t%d\t%d\n", file.getName(), size, alphaChars, numericChars, spaceChars);

                totalAlphaChars += alphaChars;
                totalNumericChars += numericChars;
                totalSpaceChars += spaceChars;
                totalSize += file.length();
            }
        }

        System.out.printf("Total Files : %d\n", totalFiles);
        System.out.printf("Total Alpha Chars : %d\n", totalAlphaChars);
        System.out.printf("Total Numeric Chars: %d\n", totalNumericChars);
        System.out.printf("Total Space Chars : %d\n", totalSpaceChars);
        System.out.printf("Total Size Disk: %s\n", getSizeString(totalSize));
    }

    private static String getSizeString(long size) {
        if (size < 1024) {
            return String.format("%d bytes", size);
        } else if (size < 1024 * 1024) {
            return String.format("%.2f KBs", size / 1024.0);
        } else if (size < 1024 * 1024 * 1024) {
            return String.format("%.2f MBs", size / (1024.0 * 1024));
        } else {
            return String.format("%.2f GBs", size / (1024.0 * 1024 * 1024));
        }
    }
}

