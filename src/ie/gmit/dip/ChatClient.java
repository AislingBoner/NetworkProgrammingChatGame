package ie.gmit.dip;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
	
	public static void main(String args[]) throws Exception {

		try {
			Socket chatSocket = new Socket("127.0.0.1", 5000); // Hardcoded - Localhost IP & Port Number 5000

			System.out.println("Successful: Connection Established with the Server.");// Client Connection Succesful

			// Opening Web-Chat Menu
			System.out.println(" ");
			System.out.println("######################################################");
			System.out.println("######### Network Web-Chat Application V1.0  #########");
			System.out.println("######################################################");
			System.out.println(" ");
			System.out.println("Welcome to the Network Chat Client!");
			System.out.println(" ");
			System.out.println("Enter the word 'quit' to exit this application.");
			System.out.println(" ");
			System.out.println("What is your Name?"); // Prompt for a username
			System.out.println(" ");

			Scanner userIn = new Scanner(System.in);
			String username = userIn.nextLine(); // username is set by the user with Scanner input
			BufferedReader socketIn = new BufferedReader(new InputStreamReader(chatSocket.getInputStream()));
			PrintStream socketOut = new PrintStream(chatSocket.getOutputStream());
			BufferedReader lineIn = new BufferedReader(new InputStreamReader(System.in));
			String line;

			while (true) {

				System.out.print("Client " + username + ": ");

				line = lineIn.readLine();
				socketOut.println(line);

				if (line.equalsIgnoreCase("quit")) {// if quit entered terminate Client Connection
					System.out.println(" ");
					System.out.println("Program Exiting... ");
					System.out.println("Client Connection Successfully Terminated ");
					break;
				}
				line = socketIn.readLine(); // Reads in sockets messages
				System.out.print("Server : " + line + " \n ");

			}

			// Cleanup - Close all connections
			chatSocket.close();
			socketIn.close();
			socketOut.close();
			lineIn.close();
			userIn.close();
			System.exit(0);

		} catch (ConnectException e) { // Throws an Error if there is no connection to the Server

			System.err.println("Issue with Connection: Connection Failed. Ensure the Server is Running.");
			System.exit(1);

		} catch (IOException e) {

			System.err.println("Issue with Input I/O for the Connection.");
			System.exit(1);

		}

	}
}
