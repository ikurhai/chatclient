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
	 * Constucteur par défaut
	 */
	public ChatClient() {
		
		// Paramétrage des différents attributs du client

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

		try {
			
			// Tentative de connexion au serveur renseigné dans les attributs
			System.out.println("Connection to " + address + " at " + port + "...");
			socket = new Socket(address, port);

			System.out.println("Connected!");

			// Une fois connecté au serveur, on crée le buffer de lecture des données envoyées par le serveur
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			// Et on crée le flux d'envoi de données vers le serveur
			out = new PrintWriter(socket.getOutputStream());	
			
			// Envoi du nom du client qui se connecte
			out.println(name);
			out.flush();

			// Récupération du MOTD du serveur (Message Of The Day pour les incultes :p)
			System.out.println("You are now connected on " + in.readLine() + ".");
			System.out.println("MOTD> " + in.readLine());
			
			// Création du processus gérant l'envoi de données vers le serveur
			new SenderThread(out).start();

			
			/* Ancien code de déconnexion
			System.out.println("Disconnection...");
			socket.close();

			System.out.println("Disconnected!");*/

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}