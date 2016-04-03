package Testing;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class Chat_ClientServeur implements Runnable {

	private Socket socket = null;
	private BufferedReader in = null;
	private PrintWriter out = null;
	private String login;
	public boolean user_error;
	private String message = null;
	
	
	public Chat_ClientServeur(Socket socket_temp, String user_temp){
		socket = socket_temp;
		login = user_temp;
		user_error=false;
	}
	public void run() {
		try {
		out = new PrintWriter(socket.getOutputStream());
		
		message = "";
		System.out.println(login+" : "+message);
		
		out.println("Bien recu");
	    out.flush();
			
		} catch (IOException e) {
			user_error=false;
		}
	}
}
