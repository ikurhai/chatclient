package fr.ikurhai.chatclient;

import java.io.PrintWriter;
import java.util.Scanner;


/**
 * Thread d'envoi de données vers le serveur
 * 
 * @author mlelievre
 * @version 0.1
 */
public class SenderThread extends Thread {
	
	private PrintWriter out;
	private Scanner s; 
	
	
	/**
	 * Constructeur 
	 * @param out Flux de sortie vers le serveur
	 */
	public SenderThread(PrintWriter out) {
		this.out = out;
	}
	
	
	/**
	 * Processus
	 */
	public void run(){
		
		s = new Scanner(System.in);
		String message;
	
		// Boucle d'envoi de données
		do {
			System.out.print("> ");
			message = s.nextLine();
			out.println(message);
			out.flush();
		} while (!message.equals("/quit"));
		// On en sort quand le client aura saisi "/quit"
		
	}

}
