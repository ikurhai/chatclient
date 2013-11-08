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
 * @version 0.3
 */
public class ChatClient {

	private Socket socket;
	private String address;
	private int port;
	private String name;
	private Scanner s;


	/**
	 * Constucteur par d�faut
	 */
	public ChatClient() {
		
		// Param�trage des diff�rents attributs du client

		this.s = new Scanner(System.in);

		System.out.println("- ChatClient -");

		System.out.print("Enter name: ");	
		this.name = s.nextLine();

		System.out.print("Enter address: ");	
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
		String serverName;

		try {
			
			// Tentative de connexion au serveur renseign� dans les attributs
			System.out.println("Connection to " + address + " at " + port + "...");
			socket = new Socket(address, port);

			System.out.println("Connected!");

			// Une fois connect� au serveur, on cr�e le buffer de lecture des donn�es envoy�es par le serveur
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			// Et on cr�e le flux d'envoi de donn�es vers le serveur
			out = new PrintWriter(socket.getOutputStream());	
			
			// Envoi du nom du client qui se connecte
			out.println(name);
			out.flush();

			// R�cup�ration de la r�ponse du serveur
			serverName = in.readLine();
			System.out.println("You are now connected on " + serverName + ".");
			
			// R�cup�ration du MOTD du serveur (Message Of The Day pour les incultes :p)
			System.out.println("MOTD> " + in.readLine());
			
			// Cr�ation du processus g�rant l'envoi de donn�es vers le serveur
			SenderThread senderThread = new SenderThread(out);
			
			senderThread.start();

			while(true){}			

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private void disconnect() {
		System.out.println("Disconnection...");
		try {
			socket.close();
			System.out.println("Disconnected!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}