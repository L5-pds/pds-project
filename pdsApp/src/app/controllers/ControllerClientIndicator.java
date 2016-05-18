package app.controllers;

import app.helpers.Serialization;
import app.listeners.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ControllerClientIndicator {
    private ListenerClientIndicator listener;

    private static Socket socket;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private Serialization s;
    
    public ControllerClientIndicator(Socket socket) {
        this.socket = socket;
        this.s = new Serialization();
        try {
          out = new PrintWriter(socket.getOutputStream());
          in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {
          javax.swing.JOptionPane.showMessageDialog(null,"Le message est : " + e.getMessage());
        }
    }
    
    public void addListener(ListenerClientIndicator l) {
        this.listener = l;
        listener.setIHM();
    }
    
    public void testmsg(String tes) {
        javax.swing.JOptionPane.showMessageDialog(null,"Le message est : " + tes);
    }
  
    public void testCountAdress()  {
        try {
        out.println("SPECIF_1/Adress/COUNT");
        out.flush();
        String response = in.readLine();
        String[] splitedQuery = response.split("/");

        String ifsuccess = splitedQuery[0];
        String resutl = splitedQuery[1];
        if(ifsuccess.equals("success")) {
            javax.swing.JOptionPane.showMessageDialog(null,"Il y a " + resutl + " adresses dans la base");
        }else {
            javax.swing.JOptionPane.showMessageDialog(null,"Erreur : " + response);
        }
      } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(null,"Le serveur ne répond plus");
      } 
    }
}
