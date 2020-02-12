package ie.gmit.dip;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {

	int port;
	ServerSocket ss;
	Socket client;
	ExecutorService pool;
	int clientsConnected = 0;

	ChatServer(int port) {
		this.port = port;
		pool = Executors.newFixedThreadPool(10); // At any one time only 10 Clients can be connected.
	}

	public void startServer() throws IOException {

		ss = new ServerSocket(5000); // Port # 5000
		System.out.println("The Chat Server is Booting up... Please wait. ");
		System.out.println(" ");
		System.out.println("######################################################");
		System.out.println("######### Network Web-Chat Application V1.0  #########");
		System.out.println("######################################################");
		System.out.println(" ");
		System.out.println("Welcome to the Network Chat Server!");
		System.out.println(" ");
		System.out.println("Enter the word 'quit' to exit this application.");
		System.out.println(" ");

		while (true) {

			client = ss.accept();// Initialize the client to server
			clientsConnected++; // When a new client is created the counter is incremented
			ServerThread runnable = new ServerThread(client, clientsConnected, this); // new instance of Game Server
																						// thread
			pool.execute(runnable);
		}

	}

	private static class ServerThread implements Runnable {

		ChatServer server;
		Socket client;
		BufferedReader clientIn;
		PrintStream clientOut;
		Scanner scan = new Scanner(System.in);
		int clientNumber;
		String s;

		ServerThread(Socket client, int count, ChatServer server) throws IOException {

			this.client = client;
			this.server = server;
			this.clientNumber = count;

			System.out.println(" ");
			System.out.println("Connection " + clientNumber + ": Established with client at " + client);
			System.out.println(" ");

			clientIn = new BufferedReader(new InputStreamReader(client.getInputStream()));
			clientOut = new PrintStream(client.getOutputStream());

		}

		@Override
		public void run() {

			try {

				while (true) {

					s = clientIn.readLine();

					System.out.print("Client( " + clientNumber + " ): " + s + " \n ");
					System.out.print("Server : ");

					s = scan.nextLine();

					if (s.equalsIgnoreCase("quit")) {

						System.out.println(" ");
						System.out.println("Program Exiting... ");
						System.out.println("Server Connection Successfully Terminated ");
						break;
					}
					clientOut.println(s);
				}

				// Cleanup & Exit - Close all connections
				clientIn.close();
				client.close();
				clientOut.close();
				System.exit(0);

			} catch (ConnectException e) { // Throws an Error if there is no connection to the Server

				System.err.println("Issue with Connection: Connection Failed. Ensure the Server is Running.");
				System.exit(1);

			} catch (IOException e) {

				System.err.println("Could not listen on port: 5000.");
				System.exit(1);

			}

		}

	}

	public static void main(String[] args) throws IOException {
		ChatServer s = new ChatServer(5000); // Initialize Port with 5000
		s.startServer();
	}

}
