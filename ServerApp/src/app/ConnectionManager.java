package app;
import java.io.*;
import java.net.*;


public class ConnectionManager implements Runnable{

  private ServerSocket serverSck = null;
  private Socket socket = null;

  public Thread connectionThread;
  public ConnectionManager(ServerSocket serverSck){
   this.serverSck = serverSck;
  }

  public void run() {

    try {
      while(true){
      socket = serverSck.accept();
      ServerInterface.changeTextLog("Un client veut se connecter : " + socket.getInetAddress());

      connectionThread = new Thread(new DBConnection(socket));
      connectionThread.start();
      }
    } catch (IOException e) {
      ServerInterface.changeTextLog("Déconnexion du client : " + socket.getInetAddress());
    }
  }
}
