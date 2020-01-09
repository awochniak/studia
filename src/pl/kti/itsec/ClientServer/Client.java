package pl.kti.itsec.ClientServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	public static void main(String[] args) {
		try {
			Socket socket = new Socket("localhost", 4444);
			System.out.println("CLIENT: Server connected on port 4444");
			
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println("CLIENT: IN and OUT streams opened. Starting sending data...");
			
			String userInput, serverResponse;
			BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
			
			while ((serverResponse = in.readLine()) != null) {
				System.out.println("Server: " + serverResponse);
				
				System.out.print("Client: ");
				userInput = console.readLine();
				out.println(userInput);
				out.flush();
				
				if (userInput.equals("koniec")) {
					break;
				}
			} 

			System.out.println("CLIENT: Ending server connection. Closing client streams and socket.");
			out.close();
			in.close();
			socket.close();
		} catch (UnknownHostException e) {
			System.err.println("CLIENT: Trying to connect to unknown host: " + e);
		} catch (Exception e) {
			System.err.println("CLIENT: Exception:  " + e);
		}

	}

}
