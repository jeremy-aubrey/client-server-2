//  Author:        Jeremy Aubrey
//
//  Program #:     4
//
//  File Name:     Server.java
//
//  Course:        COSC 4301 - Modern Programming
//
//  Due Date:      03/06/2022
//
//  Instructor:    Fred Kumi 
//
//  Description:   
//
//********************************************************************

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client
{
    //***************************************************************
    //
    //  Method:       main
    // 
    //  Description:  The main method of the project
    //
    //  Parameters:   String array
    //
    //  Returns:      N/A 
    //
    //**************************************************************
	public static void main(String[] args)
	{
		// create an object of the main class and use it to call
		// the non-static developerInfo and other non-static methods
	    Client client = new Client();
		client.developerInfo();
		
		String host = "127.0.0.1";
		int port = 4301;
		
		Socket socket = client.connect(host, port);
		client.sendRequest(socket);

	} // end main method
	
	public Socket connect(String host, int port) {
		
		Socket socket = null;
		
		try {
			// make connection to server socket
			socket = new Socket(host, port);
				
		} catch (IOException e) {
				
			System.err.println(e);
				
		}
		
		return socket;
	}
	
	public void sendRequest(Socket socket) {
		
		try {
			
			BufferedReader buffer = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			
			PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
			
			Scanner scanner = new Scanner(System.in);
			String request;
			String response;
			
			do {
				
				System.out.println("Enter a message: ");
				request = scanner.nextLine().toLowerCase();
				writer.println(request);
				
				if(!request.equals("exit")) {
					
					response = buffer.readLine();
					while(!response.equals("END")) {
						System.out.println(response);
						response = buffer.readLine();
					}
				}
				
			} while (!request.equals("exit"));
			
			//close scanner
			scanner.close();
			
			//close the socket connection
			socket.close();
			System.out.println("[ CLOSED SOCKET ]");
	
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
    //***************************************************************
    //
    //  Method:       developerInfo (Non Static)
    // 
    //  Description:  The developer information method of the program.
    //
    //  Parameters:   None
    //
    //  Returns:      N/A 
    //
    //**************************************************************
    public void developerInfo()
    {
       System.out.println("Name:    Jeremy Aubrey");
       System.out.println("Course:  COSC 4302 Operating Systems");
       System.out.println("Program: 2");

    } // end developerInfo method
    
}// end QuoteClient class