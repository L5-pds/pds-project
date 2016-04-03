package All_Server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;


public class Reception implements Runnable {

	private BufferedReader in;
	private PrintWriter out;
	private String message = null, login = null;
	
	public Reception(PrintWriter out, BufferedReader in, String login){
		this.out = out;
		this.in = in;
		this.login = login;
	}
	
	public void run() {
		
		
	        try {
	        	
			message = in.readLine();
			System.out.println(login+" : "+message);
			
			message = "Bien recu";
			out.println(message);
		    out.flush();
			
		    } catch (IOException e) {
				
		    	Interface_Serveur.changeTextLog(login +" s'est déconnecté ");
			}
		
	}

}
