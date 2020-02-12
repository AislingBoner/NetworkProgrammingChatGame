package ie.gmit.dip;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class GuessServer {

	public static void main(String[] args) throws Exception {

		try {
			ServerSocket ss = new ServerSocket(5000); // Create a New Server Socket, port 5000
			boolean running = true;

			System.out.println("The Game Server is Booting up... Please wait.");
			System.out.println("");
			System.out.println("######################################################");
			System.out.println("######## Network Guess-Game Application V1.0 #########");
			System.out.println("######################################################");
			System.out.println(" ");
			System.out.println("Welcome to the ChatGame Server!");
			System.out.println(" ");

			while (running) {
				new GuessServerThread(ss.accept()).start();

			}
			ss.close();

		} catch (IOException e) {
			System.err.println("Could not listen on Port: 5000.");
			System.exit(1);
		}

	}

	private static class GuessServerThread extends Thread {

		Socket s = null;

		public GuessServerThread(Socket s) {
			super();
			this.s = s;
		}

		public void run() {
			String userInput; // Client string Input
			int guess; // Clients Guess Input
			int counter = 0; // Number of guesses the player takes to get the correct answer
			int randomNumber = (int) (Math.random() * 100); // Create a random number between 0 and 100
			System.err.println("Client Succesfully Connected");

			try {
				Scanner in = new Scanner(s.getInputStream());
				PrintWriter out = new PrintWriter(s.getOutputStream(), true);

				while (true) {
					userInput = in.nextLine();
					guess = Integer.parseInt(userInput); // Convert string input to integer

					if (guess == randomNumber) { // If the clients guesses the correct number
						out.println("Congratulations! You are correct!");
						out.println(" It only took you " + counter + " attempts. ");

						break;

					}

					else if (guess < randomNumber) // If the clients guess is below the random number
					{
						out.println(guess + " - too low!");
						counter++; // Increase turn counter by 1
					} else if (guess > randomNumber) // If the clients guess is above the random number
					{
						out.println(guess + " - too high!");
						counter++;// Increase turn counter by 1
					} else if (userInput.equalsIgnoreCase("quit")) // If the Client types quit - Client & Server Exit
																	// Program
					{
						out.println("Exit");
						break;

					}
				}

				// Cleanup - Close all connections & Sockets to the Server
				out.close();
				in.close();
				s.close();
				System.out.println(" ");
				System.err.println("Client Connection Termintated ");
				System.out.println(" ");
				System.out.println("Program Exiting... ");
				System.out.println("Server Connection Sucessfully Terminated ");
				System.exit(0);

			} catch (Exception e) {// Exception thrown if the client disconnects with the server or quits program
				System.out.println(" ");
				System.err.println("Client Connection Termintated ");
				System.out.println(" ");
				System.out.println("Program Exiting... ");
				System.out.println("Server Connection Sucessfully Terminated ");

				System.exit(1);
			}
		}
	}

}
