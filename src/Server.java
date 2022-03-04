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
import java.util.OptionalDouble;
import java.util.stream.IntStream;

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
		
		ServerSocket socket = server.createSocket(portNumber);
		
		while (true) {
			server.startListening(socket); // process client responses
			System.out.println("[ RESUMED LISTENING ]");
		}
		
	}// end main method
	
	public ServerSocket createSocket(int portNumber) {
		
		ServerSocket socket = null;
		
		try {
			socket = new ServerSocket(portNumber);
			System.out.println("\n[ SERVER LISTENTING ON PORT " + portNumber + " ]");
			
		} catch (IOException | SecurityException | IllegalArgumentException e) {
			
			System.err.println(e);
			
		}
		
		return socket;
	}
	
	public void startListening(ServerSocket socket) {
			
		try {
			
			Socket client = socket.accept();
			System.out.println("[ CLIENT CONNECTED ]");
			
			BufferedReader input = new BufferedReader(
					new InputStreamReader(client.getInputStream()));
			PrintWriter output = new PrintWriter(client.getOutputStream(), true); // auto flush output (true)
			
			while (true) {
				
				String recievedMessage = input.readLine();
				String processed = processData(recievedMessage);
				if(recievedMessage.equals("exit")) {
					break;
				}
				output.println(processed);
			}
			
			// close the socket and resume
			// listening for connections
			client.close();
			System.out.println("[ CLIENT CLOSED ]");
		
		} catch (IOException | SecurityException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public String processData(String data) {
		
		String result = "";
		String[] strArray = data.trim().replaceAll("\\s+", ",").split(",");
		int[] intArray = new int[strArray.length];
		
		try {
			
			for(int i = 0; i < strArray.length; i++) {
				intArray[i] = Integer.parseInt(strArray[i]);
			}
			
			result = getStatisticsOrError(intArray);
			
		} catch (NumberFormatException e) {
			result = "Invalid data, must be of type integer";
		}
		
		return result + "\nEND";
		
	}
	
	public String getStatisticsOrError(int[] data) {
		
		String result = "";
		
		// validation 
		if(data.length < 3 || data.length > 3) {
			result = "Must enter 3 integers";
		} else if (data[0] <= 0 || data[1] <= 0 || data[2] <= 0) {
			result = "All integers must be greater than zero.";
		} else if(data[0] >= data[1]) {
			result = "The first integer must be less than the second";
		} else if (data[2] != 1 && data[2] != 2) {
			result = "The third integer must be 1 or 2";
		} else {
			
		int sum = getSum(getReducedData(data));
		double mean = getMean(getReducedData(data));
		int count = (int)getReducedData(data).count();
		double standardDeviation = getStandardDeviation(getReducedData(data), mean, count);
			
		// get statistics if valid
		result = String.format("%-20s %s%n%-20s %s%n%-20s %s%n", 
				"Sum: ", sum, 
				"Mean: ", mean,
				"Standard Deviation: ", String.format("%.2f", standardDeviation));
		}
		
		return result;
		
	}
	
	public IntStream getReducedData(int[] data) {
		IntStream reducedData;
		
		if(data[2] % 2 == 0) { // process evens
			reducedData = IntStream.rangeClosed(data[0], data[1])
					.filter(num -> num % 2 == 0); //filter out odds

		} else { // process odds
			reducedData = IntStream.rangeClosed(data[0], data[1])
					.filter(num -> num % 2 == 1); //filter out evens
		}
		
		return reducedData;
	}
	
	public int getSum(IntStream data) {
		
		return data.sum();
	}
	
	public int getMean(IntStream data) {
		
		double mean = 0;
		
		OptionalDouble result = data.average();
		
		if(result.isPresent()) {
			mean = result.getAsDouble();
		}
		
		return (int) mean;
	}
	
	public double getStandardDeviation(IntStream data, double mean, int count) {

		double sumOfSquares = data.mapToDouble(num -> Double.valueOf(num)) 
				.map(num -> Math.pow((num - mean), 2)) // square distance to mean
				.sum(); // sum squares
		
		return Math.sqrt(sumOfSquares / count); // get square root of sum / count 
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