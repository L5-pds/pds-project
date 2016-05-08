package app.controllers;

import app.listeners.*;
import app.models.*;

import java.io.*;
import java.net.*;

import java.io.*;
import java.net.*;
import java.sql.*;

public class WelcomeController {
  private WelcomeListener listener;
  private ServerSocket serverSck = null;
  private Socket socket = null;
  public Server server;
  public Thread connectionThread;

  public WelcomeController (Server s) {
    this.server = s;
  }

  public void addListener(WelcomeListener l) {
    listener = l;
  }

  public void launch(){
    try {
      serverSck = new ServerSocket(1234);
      listener.changeTextLog("Le serveur est en ligne.");
      listener.changeTextLog("Le serveur est à l'écoute du port "+serverSck.getLocalPort());

      listener.changeTextLog("Création du pool:");
      server.createConnectionPool();
      listener.changeTextLog(server.connectionPool.length + " connexions créées.");
      listener.updateInfoLabel();

      start();
    } catch (IOException e) {
      listener.changeTextLog("WARNING : Problème ouverture du port (" + e.getMessage() + ")");
    }
  }

  public void start() {
    Thread t = new Thread(new Runnable() {
      public void run() {
        try {
          while(true){
            socket = serverSck.accept();
            listener.changeTextLog("Un client veut se connecter : " + socket.getInetAddress());

            connectionThread = new Thread(new DBConnection(socket, listener));// A revoir
            connectionThread.start();
          }
        } catch (IOException e) {
          listener.changeTextLog("Déconnexion du client : " + socket.getInetAddress());
        }
      }
    });
    t.start();
  }
}
