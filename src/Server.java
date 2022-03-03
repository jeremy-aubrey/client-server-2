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
		
		int portNumber = 4301;
		
		server.startListening(portNumber);
		
		
	}// end main method
	
	public void startListening(int portNumber) {
		
		try {
			ServerSocket sock = new ServerSocket(portNumber);
			System.out.println("\n[ SERVER LISTENTING ON PORT " + portNumber + " ]");
			Socket client = sock.accept();
			System.out.println("[ CLIENT CONNECTED ]");
			
			respondToInput(client); // process client responses
			
			// close the socket and resume
			// listening for connections
			client.close();
			System.out.println("[ CLOSED SOCKET ]");
			
		} catch (IOException ioe) {
			
			System.err.println(ioe);
			
		}
	}
	
	public void respondToInput(Socket client) {
			
		try {
			
			BufferedReader input = new BufferedReader(
					new InputStreamReader(client.getInputStream()));
			PrintWriter output = new PrintWriter(client.getOutputStream(), true); // auto flush output (true)
			
			while (true) {
				String recievedMessage = input.readLine();
				String processed = processData(recievedMessage);
				if(recievedMessage.equals("exit")) {
					break;
				}
				output.println("echo from server: " + processed);
			}
		
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public String processData(String data) {
		
		String result = "";
		String[] strArray = data.split(" ");
		int[] intArray = new int[strArray.length];
		
		try {
			
			for(int i = 0; i < strArray.length; i++) {
				intArray[i] = Integer.parseInt(strArray[i]);
			}
			
			result = getStatisticsOrError(intArray);
			
		} catch (NumberFormatException e) {
			result = "Invalid data, must be of type integer";
		}
		
		return result;
		
	}
	
	public String getStatisticsOrError(int[] data) {
		String result = "";
		if(data.length < 3 || data.length > 3) {
			result = "Must enter 3 integers";
		} else if (data[0] <= 0 || data[1] <= 0 || data[2] <= 0) {
			result = "All integers must be greater than zero.";
		} else if(data[0] > data[1]) {
			result = "The first integer must be less than the second";
		} else if (data[2] != 1 && data[2] != 2) {
			result = "The third integer must be 1 or 2";
		
		} else {
			result = "valid data : " + arrToString(data);
		}
		
		return result;
		
	}
	
	public String arrToString(int[] arr) {
		StringBuffer result = new StringBuffer();
		for(int num : arr) {
			result.append(num);
		}
		
		return result.toString();
		
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
    
}// end QuoteServer class