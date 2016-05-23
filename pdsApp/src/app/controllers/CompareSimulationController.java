package app.controllers;

import app.helpers.*;
import app.listeners.*;
import java.io.*;
import java.net.*;

public class CompareSimulationController {
  private CompareSimulationListener listener;

  private static Socket socket;
  private PrintWriter out = null;
  private BufferedReader in = null;
  private Serialization s;

  public CompareSimulationController(Socket socket) {
    this.socket = socket;
    this.s = new Serialization();
    try {
      out = new PrintWriter(socket.getOutputStream());
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    } catch (Exception e) {
      javax.swing.JOptionPane.showMessageDialog(null,"Le message est : " + e.getMessage());
    }
  }

  public void addListener(CompareSimulationListener l) {
      this.listener = l;
      listener.setIHM();
  }
}
