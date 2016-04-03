package All_Client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Launcher {

	private static Socket socket = null;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			socket = new Socket("127.0.0.1",1234);
			Interface_Client LeClient = new Interface_Client(socket);
			LeClient.setVisible(true);
			
		} catch (UnknownHostException e) {
			javax.swing.JOptionPane.showMessageDialog(null,"Impossible de se connecter à l'adresse "+socket.getLocalAddress(), "Alerte", javax.swing.JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			javax.swing.JOptionPane.showMessageDialog(null,"Aucun serveur à l'écoute du port ", "Alerte", javax.swing.JOptionPane.ERROR_MESSAGE);
		}
	}
}
