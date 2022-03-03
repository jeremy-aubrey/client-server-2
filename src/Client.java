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
		
		try {
			// make connection to server socket
			Socket sock = new Socket("127.0.0.1", 4301);
			
			BufferedReader echoes = new BufferedReader(
					new InputStreamReader(sock.getInputStream()));
			
			PrintWriter stringToEcho = new PrintWriter(sock.getOutputStream(), true);
			
			Scanner scanner = new Scanner(System.in);
			String echoString;
			String response;
			
			do {
				System.out.println("Enter a message: ");
				echoString = scanner.nextLine();
				stringToEcho.println(echoString);
				
				if(!echoString.equals("exit")) {
					response = echoes.readLine();
					System.out.println(response);
				}
			} while (!echoString.equals("exit"));
			
			
			//close the socket connection
			sock.close();
			System.out.println("[ CLOSED SOCKET ]");
				
		} catch (IOException ioe) {
				
			System.err.println(ioe);
				
		}

	} // end main method
	
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