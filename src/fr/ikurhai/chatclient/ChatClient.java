package fr.ikurhai.chatclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


/**
 * Classe client du chat
 * 
 * @author ikurhai
 * @version 0.1
 */
public class ChatClient {
	
	private Socket socket;
	private String address;
    private int port;
    private Scanner s;
    
    
    /**
     * Constucteur par défaut
     */
    public ChatClient() {
    	
    	this.s = new Scanner(System.in);
    	
    	System.out.println("- ChatClient -");

		System.out.print("Enter adress: ");	
		this.address = s.nextLine();
		
		System.out.print("Enter port: ");	
		this.port = s.nextInt();
    	
    }
    
    
    /**
     * Lance le processus du client
     */
    public void run() {
    	
        BufferedReader in;
        String message;
    	
    	try {

            System.out.println("Connection to " + address + " at " + port + "...");
            socket = new Socket(address, port);

            System.out.println("Connected!");

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            do {
                message = in.readLine();
                System.out.println(message);
            } while (!message.equals("Disconnected."));
            
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