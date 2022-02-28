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
import java.net.ServerSocket;
import java.net.Socket;

public class Server
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
		Server server = new Server();
		server.developerInfo();
		
		try {
			
				ServerSocket sock = new ServerSocket(4301);
				Socket client = sock.accept();
				System.out.println("Client Connected");
				BufferedReader input = new BufferedReader(
						new InputStreamReader(client.getInputStream()));
				
				PrintWriter output = new PrintWriter(client.getOutputStream(), true); // auto flush output (true)
				
				// listen for connections
				while (true) {
				String echoString = input.readLine();
					if(echoString.equals("exit")) {
						break;
					}
					output.println("echo from server: " + echoString + "!!!!@#%$^$");
				}
				
				// close the socket and resume
				// listening for connections
				client.close();
				
				
				
			} catch (IOException ioe) {
				
				System.err.println(ioe);
				
			}
		
	}// end main method
	
	
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
    
}// end QuoteServer class