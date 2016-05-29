package app.controllers;

import app.listeners.*;
import app.models.*;

import java.io.*;
import java.net.*;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WelcomeControllerServer {
  private WelcomeListenerServer listener;
  private ServerSocket serverSck = null;
  private Socket socket = null;
  public Server server;
  public Thread connectionThread;
  private Thread t;

  public WelcomeControllerServer (Server s) {
    this.server = s;
  }

  public void addListener(WelcomeListenerServer l) {
    listener = l;
  }

  public void launch(){
    try {
      serverSck = new ServerSocket(1234);
      listener.changeTextLog("SERVER - online");
      listener.changeTextLog("SERVER - port "+serverSck.getLocalPort());

      listener.changeTextLog("POOL - create");
      server.createConnectionPool();
      listener.changeTextLog("POOL - " + server.connectionPool.length + " connection created.");
      listener.updateInfoLabel();

      start();
    } catch (IOException e) {
      listener.changeTextLog("WARNING_SERVER - port opening problem (" + e.getMessage() + ")");
    }
  }

  public void start() {
    t = new Thread(new Runnable() {
      public void run() {
        try {
          while(true){
            socket = serverSck.accept();
            listener.changeTextLog("SERVER - " + socket.getInetAddress() + " - try");

            connectionThread = new Thread(new UserCommunicate(socket, listener));
            connectionThread.start();
          }
        } catch (IOException e) {
          listener.changeTextLog("SERVER - new client - gone");
        }
      }
    });
    t.start();
  }
  
}
