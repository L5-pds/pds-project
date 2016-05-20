/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controllers;

import app.helpers.*;
import app.listeners.*;
import java.io.*;
import java.net.*;
/**
 *
 * @author Thibault
 */
public class ControllerMariam {
    private ListenerMariam listener;

    private static Socket socket;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private Serialization s;

    public ControllerMariam(Socket socket) {
        this.socket = socket;
        this.s = new Serialization();
        try {
          out = new PrintWriter(socket.getOutputStream());
          in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {
          javax.swing.JOptionPane.showMessageDialog(null,"Le message est : " + e.getMessage());
        }
    }

    public void addListener(ListenerMariam l) {
        this.listener = l;
        listener.setIHM();
    }
}
