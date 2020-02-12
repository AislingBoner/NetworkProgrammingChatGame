package ie.gmit.dip;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.*;
import java.net.*;

public class GuessClient {

	public static void main(String[] args) throws IOException {

		try {
			Socket guessGameSocket = new Socket("127.0.0.1", 5000); // Hardcoded - Localhost IP & Port Number 5000

			System.out.println("Successful: Connection Established with the Server."); // Let the user know connection
																						// has been made.

			Scanner socketIn = new Scanner(guessGameSocket.getInputStream()); // This is the input from the socket
			PrintWriter socketOut = new PrintWriter(guessGameSocket.getOutputStream(), true); // Output from the socket
			Scanner scan = new Scanner(System.in); // Sets the keyboard as input for user

			System.out.println(" ");
			System.out.println("######################################################");
			System.out.println("######## Network Guess-Game Application V1.0 #########");
			System.out.println("######################################################");
			System.out.println(" ");
			System.out.println("Welcome to the ChatGame Client!");
			System.out.println(" ");
			System.out.println("What is your Name?"); // Prompt for a username.

			String username = scan.nextLine(); // username is equal to inputted name.
			String server = "";

			System.out.println("######################################################");
			System.out.println("###### Lets Play A Game Called Guess the Number ######");
			System.out.println("######################################################");
			System.out.println(" ");
			System.out.println("Hello " + username + " !!");
			System.out.println(" ");

			System.out.println("Game Description:");
			System.out.println("The Server has generated a Random Number between  0 - 100.");
			System.out.println(" ");
			System.out.println("To Play, You must try and guess what the number is and ");
			System.out.println("then the server will return whether you are correct or ");
			System.out.println("failed and give you a hint!! ");
			System.out.println(" ");
			System.out.println("Enter the word 'quit' to exit this application.");
			System.out.println(" ");

			while (true) {
				String userInput = scan.nextLine(); // Get input from the keyboard
				socketOut.println(userInput); // Send user input to the socket
				server = socketIn.nextLine(); // Socket Response
				System.out.println(server);

				if (server.equalsIgnoreCase("Congratulations! You are correct!")) {
					System.out.println("Thank you for playing 'Guess the number'");
					System.out.println("Restart to Play Again");
					break; // Close the connection to server

				} else if (server.equalsIgnoreCase("Exit")) {
					break;
				}

			}

			// Cleanup - Close all connections & Sockets to the Server
			System.out.println(" ");
			System.out.println("Program Exiting... ");
			System.out.println("Client Connection Successfully Terminated ");
			socketOut.close();
			scan.close();
			socketIn.close();
			guessGameSocket.close();
			System.exit(0);

		} catch (ConnectException e) { // Throws Error if there is No connection to the Server

			System.err.println("Issue with Connection: Connection Failed. Ensure the Server is Running.");
			System.exit(1);

		} catch (IOException e) {// Throws Error if there is an issue with IO
			System.err.println("Issue with Input I/O for the Connection.");
			System.exit(1);
		} catch (NoSuchElementException e) {// Throws Error if there is an issue with IO
			System.out.println(" ");
			System.err.println("Client Connection Termintated ");
			System.out.println("Program Exiting... ");
			System.exit(1);
		}
	}
}
