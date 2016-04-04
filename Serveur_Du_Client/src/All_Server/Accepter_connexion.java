package All_Server;
import java.io.*;
import java.net.*;


public class Accepter_connexion implements Runnable{

	private ServerSocket socketserver = null;
	private Socket socket = null;

	public Thread new_client;
	public Accepter_connexion(ServerSocket socket_temp){
	 socketserver = socket_temp;
	}
	
	public void run() {
		
		try {
			while(true){
			//comment	
			socket = socketserver.accept();
			Interface_Serveur.changeTextLog("Un client veut se connecter : " + socket.getInetAddress());
			
			new_client = new Thread(new Communication_client(socket));
			new_client.start();
			
			}
		} catch (IOException e) {
			Interface_Serveur.changeTextLog("Déconnexion du client : " + socket.getInetAddress());
		}
	}
}
