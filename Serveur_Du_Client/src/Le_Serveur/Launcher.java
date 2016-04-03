package Le_Serveur;

import java.io.*;
import java.net.*;

public class Launcher {

	private static String        _message = "Bonjour, je suis votre server.";
	private static int           _port;
	private static ServerSocket  _socket;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try
		{
			_port   = (args.length == 1) ? Integer.parseInt(args[0]) : 1234;
			_socket = new ServerSocket(_port);

			System.out.println("Serveur TCP en ligne sur le port " + _port + "...");

			while (true)
			{
				// Accept new TCP client
				Socket client       = _socket.accept();
				// Open output stream
				OutputStream output = client.getOutputStream();

				System.out.println("New client, address " + client.getInetAddress() + " on " + client.getPort() + ".");

				// Write the message and close the connection
				output.write(_message.getBytes());
				client.close();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				_socket.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
	}

}
