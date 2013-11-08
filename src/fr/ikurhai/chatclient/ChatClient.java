package fr.ikurhai.chatclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


/**
 * Classe client du chat
 * 
 * @author ikurhai
 * @version 0.2
 */
public class ChatClient {

	private Socket socket;
	private String address;
	private int port;
	private String name;
	private Scanner s;


	/**
	 * Constucteur par défaut
	 */
	public ChatClient() {

		this.s = new Scanner(System.in);

		System.out.println("- ChatClient -");

		System.out.print("Enter name: ");	
		this.name = s.nextLine();

		System.out.print("Enter adress: ");	
		this.address = s.nextLine();

		System.out.print("Enter port: ");	
		this.port = s.nextInt();
		s.nextLine();

	}


	/**
	 * Lance le processus du client
	 */
	public void run() {

		PrintWriter out;  
		BufferedReader in;
		String message;

		try {

			System.out.println("Connection to " + address + " at " + port + "...");
			socket = new Socket(address, port);

			System.out.println("Connected!");

			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());	
			s = new Scanner(System.in);
			
			out.println(name);
			out.flush();

			// MOTD
			System.out.println("You are now connected on " + in.readLine() + ".");
			System.out.println("MOTD> " + in.readLine());
			
			do {
				System.out.print("> ");
				message = s.nextLine();
				out.println(message);
				out.flush();
			} while (!message.equals("/quit"));

			System.out.println("Disconnection...");
			socket.close();

			System.out.println("Disconnected!");

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}